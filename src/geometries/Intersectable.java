package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/**
 * An interface to find intersections of rays with geometries.
 *
 * @author Chani & Sara Lea
 */
public interface Intersectable {
    /**
    * @param ray- check the intersections between it and the Geometry shape.
     * @return a list of all the intersections points.
    */
    public List<Point3D> findIntersections(Ray ray);
}
