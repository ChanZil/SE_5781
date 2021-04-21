package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Sphere class represent the shape shere
 *
 * @author Chani & Sara Lea
 */
public class Sphere implements Geometry{
    Point3D center;
    double radius;

    /**
     * constructor- gets a point and radius and create a Sphere
     * @param center the center point of the sphere
     * @param radius the radius of the sphere
     */
    public Sphere(Point3D center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Point3D getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }

    @Override
    public Vector getNormal(Point3D point) {
        Vector v = point.subtract(center);
        return v.normalize();
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        Point3D P0 = ray.getpO();
        Vector v = ray.getDir();
        //if the ray starts in the center of the sphere, by adding the radius to the center
        //with the direction of the ray, we get the intersection with the sphere.
        if (P0.equals(center)) {
            return List.of(center.add(v.scale(radius)));
        }
        Vector U = center.subtract(P0); //a vector from the center of the sphere to the start of the ray
        double tm = alignZero(v.dotProduct(U));
        double d = alignZero(Math.sqrt(U.lengthSquared() - tm * tm)); //the distance between the center and the ray
        // if the distance is bigger than the radius- there are no intersections with the sphere
        if (d >= radius) {
            return null;
        }
        double th = alignZero(Math.sqrt(radius * radius - d * d));
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);
        //if there are 2 intersections with the sphere- the ray starts outside the sphere
        //and passes through it- calculates the 2 points of the intersections with the sphere and return them.
        if (t1 > 0 && t2 > 0) {
            Point3D P1 = ray.getPoint(t1);
            Point3D P2 = ray.getPoint(t2);
            return List.of(P1, P2);
        }
        //if the ray starts outside the sphere, passes through it and ends inside of the sphere- there
        //is one intersection.
        if (t1 > 0) {
            Point3D P1 = ray.getPoint(t1);
            return List.of(P1);
        }
        //if the ray starts inside the sphere and ends outside of it- there is one intersection.
        if (t2 > 0) {
            Point3D P2 =ray.getPoint(t2);
            return List.of(P2);
        }
        return null;
    }
}
