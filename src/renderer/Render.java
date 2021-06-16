package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.util.List;
import java.util.MissingResourceException;

/**
 *
 *
 * @author Chani & Sara Lea
 */
public class Render {
    ImageWriter imageWriter;
    Camera camera;
    RayTracerBase rayTracerBase;

    /**
     * set the value of the image writer
     * @param imageWriter the new image writer
     * @return the render
     */
    public Render setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * set the value of the camera
     * @param camera the new camera
     * @return the render
     */
    public Render setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    /**
     * set the value of the ray tracer base
     * @param rayTracerBase the new tracer base
     * @return the render
     */
    public Render setRayTracer(RayTracerBase rayTracerBase) {
        this.rayTracerBase = rayTracerBase;
        return this;
    }

    /**
     * rendering an image
     */
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
     */
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
     */
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
    public Color averageOfColor(List<Ray> rays) {
        Color color = Color.BLACK;
        for (Ray n : rays){
            color = color.add(rayTracerBase.traceRay(n));
        }
        return color.scale(1.d/rays.size());
    }
}
