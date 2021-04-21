package geometries;

import primitives.*;

import java.util.List;
import static primitives.Util.*;

/**
 * Triangle class represent the shape triangle
 *
 * @author Chani & Sara Lea
 */
public class Triangle extends Polygon{
    /**
     * constructor- gets 3 points and create a triangle (made of Polygon)
     *
     * @param p1
     * @param p2
     * @param p3
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        super(p1, p2, p3);
    }

    @Override
    public String toString() {
        return "Triangle{} " + super.toString();
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> result = plane.findIntersections(ray); //the intersections with the plane of the triangle
        //if there are no intersections with the plane, there are no intersections at all.
       if (result == null)
            return result;
        //the ray:
        Point3D P0 = ray.getpO();
        Vector v = ray.getDir();
        //the points of the triangle:
        Point3D p1 = vertices.get(0);
        Point3D p2 = vertices.get(1);
        Point3D p3 = vertices.get(2);
        //the vectors of the triangle:
        Vector p1p0 = p1.subtract(P0);
        Vector p2p0 = p2.subtract(P0);
        Vector p3p0 = p3.subtract(P0);
        //check with every normal of the triangle if it vertical to the ray
        double res = alignZero(v.dotProduct(p1p0.crossProduct(p2p0)));
        if(isZero(res) || res > 0)
            return null;
        res = alignZero(v.dotProduct(p2p0.crossProduct(p3p0)));
        if(isZero(res) || res > 0)
            return null;
        res = alignZero(v.dotProduct(p3p0.crossProduct(p1p0)));
        if(isZero(res) || res > 0)
            return null;
        return result;
    }
}
