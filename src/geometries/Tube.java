package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Tube class represent the shape Tube
 *
 * @author Chani & Sara Lea
 */
public class Tube implements Geometry{
    Ray axisRay;
    double radius;

    /**
     * gets a ray and the radius of the tube and creates a Tube
     *
     * @param axisRay
     * @param radius the radius of the tube
     */
    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }

    public Ray getAxisRay() {
        return axisRay;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }

    @Override
    public Vector getNormal(Point3D point) {
        Vector v = point.subtract(axisRay.getpO());
        return v.normalize();
    }
}
