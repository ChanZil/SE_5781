package renderer;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * RayTracerBasic class represent
 *
 * @author Chani & Sara Lea
 */
public class RayTracerBasic extends RayTracerBase{

    /**
     *
     * @param scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * trace ray- get a ray and return the color of the closest intersection point with the ray
     * @param ray the ray
     * @return color
     */
    @Override
    public Color traceRay(Ray ray) {
        //find all the intersections of the ray with the geometries
        List<Point3D> intersections = scene.geometries.findIntersections(ray); 
        //if there are intersections
        if (intersections != null) {
            Point3D closestPoint = ray.findClosestPoint(intersections); //find the closest intersection point with the ray
            return calcColor(closestPoint); //return the color of the closest intersection point
        }//else- if there are no intersection points
        return scene.background; //return the background of the scene
    }

    /**
     * get a point and return the color of this point
     * @param point the point
     * @return the color of the point
     */
    private Color calcColor(Point3D point) {
        return scene.ambientLight.getIntensity();
    }
}
