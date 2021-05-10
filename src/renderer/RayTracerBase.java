package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * RayTracerBase class represent
 *
 *  @author Chani & Sara Lea
 */
public abstract class RayTracerBase {
    protected Scene scene;

    /**
     * main constructor
     * @param scene
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     *
     * @param ray
     * @return
     */
    public abstract Color traceRay(Ray ray);
}
