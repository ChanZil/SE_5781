package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

/**
 * Renderer class is responsible for generating pixel color map from a graphic
 * scene, using ImageWriter class
 *
 * @author Dan
 *
 */
public class Render {
    private Camera camera;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracerBase;
    private static final String RESOURCE_ERROR = "Renderer resource not set";
    private static final String RENDER_CLASS = "Render";
    private static final String IMAGE_WRITER_COMPONENT = "Image writer";
    private static final String CAMERA_COMPONENT = "Camera";
    private static final String RAY_TRACER_COMPONENT = "Ray tracer";

    private int threadsCount = 0;
    private static final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
    private boolean print = false; //printing progress percentage

    /**
     * Set multi-threading <br>
     * - if the parameter is 0 - number of cores less 2 is taken
     *
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
        if (threads != 0)
            this.threadsCount = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            this.threadsCount = cores <= 2 ? 1 : cores;
        }
        return this;
    }

    /**
     * Set debug printing on
     *
     * @return the Render object itself
     */
    public Render setDebugPrint() {
        print = true;
        return this;
    }

    /**
     * Pixel is an internal helper class whose objects are associated with a Render
     * object that they are generated in scope of. It is used for multithreading in
     * the Renderer and for follow up its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each
     * thread.
     *
     * @author Dan
     *
     */
    private class Pixel {
        private long maxRows = 0;
        private long maxCols = 0;
        private long pixels = 0;
        public volatile int row = 0;
        public volatile int col = -1;
        private long counter = 0;
        private int percents = 0;
        private long nextCounter = 0;

        /**
         * The constructor for initializing the main follow up Pixel object
         *
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            this.maxRows = maxRows;
            this.maxCols = maxCols;
            this.pixels = (long) maxRows * maxCols;
            this.nextCounter = this.pixels / 100;
            if (Render.this.print)
                System.out.printf("\r %02d%%", this.percents);
        }

        /**
         * Default constructor for secondary Pixel objects
         */
        public Pixel() {
        }

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object
         * - this function is critical section for all the threads, and main Pixel
         * object data is the shared data of this critical section.<br/>
         * The function provides next pixel number each call.
         *
         * @param target target secondary Pixel object to copy the row/column of the
         *               next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print,
         *         if it is -1 - the task is finished, any other value - the progress
         *         percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++this.counter;
            if (col < this.maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (Render.this.print && this.counter == this.nextCounter) {
                    ++this.percents;
                    this.nextCounter = this.pixels * (this.percents + 1) / 100;
                    return this.percents;
                }
                return 0;
            }
            ++row;
            if (row < this.maxRows) {
                col = 0;
                target.row = this.row;
                target.col = this.col;
                if (Render.this.print && this.counter == this.nextCounter) {
                    ++this.percents;
                    this.nextCounter = this.pixels * (this.percents + 1) / 100;
                    return this.percents;
                }
                return 0;
            }
            return -1;
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         *
         * @param target target secondary Pixel object to copy the row/column of the
         *               next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percent = nextP(target);
            if (Render.this.print && percent > 0)
                synchronized (this) {
                    notifyAll();
                }
            if (percent >= 0)
                return true;
            if (Render.this.print)
                synchronized (this) {
                    notifyAll();
                }
            return false;
        }

        /**
         * Debug print of progress percentage - must be run from the main thread
         */
        public void print() {
            if (Render.this.print)
                while (this.percents < 100)
                    try {
                        synchronized (this) {
                            wait();
                        }
                        System.out.printf("\r %02d%%", this.percents);
                        System.out.flush();
                    } catch (Exception e) {
                    }
        }
    }

    /**
     * Camera setter
     *
     * @param camera to set
     * @return renderer itself - for chaining
     */
    public Render setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    /**
     * Image writer setter
     *
     * @param imgWriter the image writer to set
     * @return renderer itself - for chaining
     */
    public Render setImageWriter(ImageWriter imgWriter) {
        this.imageWriter = imgWriter;
        return this;
    }

    /**
     * Ray tracer setter
     *
     * @param tracer to use
     * @return renderer itself - for chaining
     */
    public Render setRayTracer(RayTracerBase tracer) {
        this.rayTracerBase = tracer;
        return this;
    }

    /**
     * Produce a rendered image file
     */
    public void writeToImage() {
        if (imageWriter == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);

        imageWriter.writeToImage();
    }

    /**
     * Cast ray from camera in order to color a pixel
     * @param nX resolution on X axis (number of pixels in row)
     * @param nY resolution on Y axis (number of pixels in column)
     * @param col pixel's column number (pixel index in row)
     * @param row pixel's row number (pixel index in column)
     */
    private void castRay(int nX, int nY, int col, int row) {
        Ray ray = camera.constructRayThroughPixel(nX, nY, col, row);
        Color color = rayTracerBase.traceRay(ray);
        imageWriter.writePixel(col, row, color);
    }

    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object - with multi-threading
     */
    private void renderImageThreaded() {
        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();
        final Pixel thePixel = new Pixel(nY, nX);
        // Generate threads
        Thread[] threads = new Thread[threadsCount];
        for (int i = threadsCount - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel();
                while (thePixel.nextPixel(pixel))
                    castRay(nX, nY, pixel.col, pixel.row);
            });
        }
        // Start threads
        for (Thread thread : threads)
            thread.start();

        // Print percents on the console
        thePixel.print();

        // Ensure all threads have finished
        for (Thread thread : threads)
            try {
                thread.join();
            } catch (Exception e) {
            }

        if (print)
            System.out.print("\r100%");
    }

    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object
     */
    public void renderImage() {
        if (imageWriter == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);
        if (camera == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, CAMERA_COMPONENT);
        if (rayTracerBase == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, RAY_TRACER_COMPONENT);

        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();
        if (threadsCount == 0)
            for (int i = 0; i < nY; ++i)
                for (int j = 0; j < nX; ++j)
                    castRay(nX, nY, j, i);
        else
            renderImageThreaded();
    }

    /**
     * Create a grid [over the picture] in the pixel color map. given the grid's
     * step and color.
     *
     * @param step  grid's step
     * @param color grid's color
     */
    public void printGrid(int step, Color color) {
        if (imageWriter == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        for (int i = 0; i < nY; ++i)
            for (int j = 0; j < nX; ++j)
                if (j % step == 0 || i % step == 0)
                    imageWriter.writePixel(j, i, color);
    }
    //Mini project 1:


    /**
     *  A method that will first check that a blank value was entered
     *  in all the fields and in case of lack throws a suitable deviation
     *  for each pixel a beam will be built and for each beam we
     *  will get a color from average of all the color from the rays list
     *  The women color in the appropriate pixel of the image manufacturer
     */
    public void renderImageSuperSampling() {
        try {
            if (imageWriter == null)
                throw new MissingResourceException("No image writer", ImageWriter.class.getName(), "");
            if (camera == null)
                throw new MissingResourceException("No camera", Camera.class.getName(), "");
            if (rayTracerBase == null)
                throw new MissingResourceException("No ray trace base", RayTracerBase.class.getName(), "");
            int nX = imageWriter.getNx();
            int nY = imageWriter.getNy();
            //pass every pixel of the image
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    //get list of rays from the same one pixel
                    List<Ray> rays = camera.constructRayThroughPixelSuperSample(nX, nY, j, i);
                    //sending the rays to function that calculate the average of all the color of the rays
                    Color k = averageOfColor(rays);
                    imageWriter.writePixel(j, i, k);
                }
            }
        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
        }
    }

    /**
     *
     * @param rays
     * @return
     */
    public Color averageOfColor(List<Ray> rays) {
        Color color = Color.BLACK;
        for (Ray n : rays){
            color = color.add(rayTracerBase.traceRay(n));
        }
        return color.scale(1.d/rays.size());
    }
}

//Mimi project 2:


/*
/**
 *
 *
 * @author Chani & Sara Lea
 /
public class Render {

    ImageWriter imageWriter;
    Camera camera;
    RayTracerBase rayTracerBase;

    /**
     * set the value of the image writer
     * @param imageWriter the new image writer
     * @return the render
     /
    public Render setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * set the value of the camera
     * @param camera the new camera
     * @return the render
     /
    public Render setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    /**
     * set the value of the ray tracer base
     * @param rayTracerBase the new tracer base
     * @return the render
     /
    public Render setRayTracer(RayTracerBase rayTracerBase) {
        this.rayTracerBase = rayTracerBase;
        return this;
    }

    /**
     * rendering an image
     /
    public void renderImage(){
        try {
            if (imageWriter == null)
                throw new MissingResourceException("No image writer", ImageWriter.class.getName(), "");
            if (camera == null)
                throw new MissingResourceException("No camera", Camera.class.getName(), "");
            if (rayTracerBase == null)
                throw new MissingResourceException("No ray trace base", RayTracerBase.class.getName(), "");
            int nX = imageWriter.getNx();
            int nY = imageWriter.getNy();
            //pass every pixel of the image
            for (int i = 0; i < nY; i++)
                for (int j = 0; j < nX; j++) {
                    Ray ray = camera.constructRayThroughPixel(nX, nY, j, i); //construct a ray for every pixel
                    Color pixelColor = rayTracerBase.traceRay(ray); //get the color of the ray from the ray tracer
                    imageWriter.writePixel(j, i, pixelColor); //put the color in the right pixel
                }
        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException("" + e.getClassName());
        }
    }

    /**
     * color the grid in an image
     * @param interval the intervals between the squares
     * @param color the color of the lines
     /
    public void printGrid(int interval, Color color){
        if (imageWriter == null)
            throw new MissingResourceException("No image writer", ImageWriter.class.getName(), "");
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        //pass every pixel of the image
        for (int i = 0; i < nY; i++)
            for (int j = 0; j < nX; j++)
                //color only the pixels of the grid
                if (i % interval == 0 || j % interval == 0)
                    imageWriter.writePixel(j, i, color);
    }

    /**
     *
     /
    public void writeToImage(){
        if (imageWriter == null) //check if imageWriter has a value
            throw new MissingResourceException("No image writer", ImageWriter.class.getName(), "");
        imageWriter.writeToImage();
    }

    /**
     * A method that will first check that a blank value was entered
     * in all the fields and in case of lack throws a suitable deviation
     * for each pixel a beam will be built and for each beam we
     * will get a color from average of all the color from the rays list
     * The women color in the appropriate pixel of the image manufacturer
     /
    public void renderImageSuperSampling() {
        try {
            if (imageWriter == null)
                throw new MissingResourceException("No image writer", ImageWriter.class.getName(), "");
            if (camera == null)
                throw new MissingResourceException("No camera", Camera.class.getName(), "");
            if (rayTracerBase == null)
                throw new MissingResourceException("No ray trace base", RayTracerBase.class.getName(), "");
            int nX = imageWriter.getNx();
            int nY = imageWriter.getNy();
            //pass every pixel of the image
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    //get list of rays from the same one pixel
                    List<Ray> rays = camera.constructRayThroughPixelSuperSample(nX, nY, j, i);
                    //sending the rays to function that calculate the average of all the color of the rays
                    Color k = averageOfColor(rays);
                    imageWriter.writePixel(j, i, k);
                }
            }
        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
        }
    }
    public Color averageOfColor(List<Ray> rays) {
        Color color = Color.BLACK;
        for (Ray n : rays){
            color = color.add(rayTracerBase.traceRay(n));
        }
        return color.scale(1.d/rays.size());
    }
}
*/



