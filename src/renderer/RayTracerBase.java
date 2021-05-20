package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * RayTracerBase class represent the basic ray scanner
 *
 *  @author Chani & Sara Lea
 */
public abstract class RayTracerBase {
    protected Scene scene; //the scene that the ray tracer scan

    /**
     * the constructor of RatTracerBase
     * @param scene the scene that the ray tracer scan
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * get a ray and trace the scene with it. find the color of the intersection point with the ray
     * @param ray the ray that trace the scene
     * @return the color of the intersection point
     */
    public abstract Color traceRay(Ray ray);
}
