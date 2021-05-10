package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;
import scene.Scene;
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
    public Render setRayTracerBase(RayTracerBase rayTracerBase) {
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
        if (imageWriter == null) //check if inageWriter has a value
            throw new MissingResourceException("No image writer", ImageWriter.class.getName(), "");
        imageWriter.writeToImage();
    }
}
