package geometries;

import primitives.Point3D;
import primitives.Vector;

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
        return null;
    }
}
