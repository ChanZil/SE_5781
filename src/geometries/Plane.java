package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Plane class represent the shape plane
 *
 * @author Chani & Sara Lea
 */
public class Plane implements Geometry{
    Point3D q0;
    Vector normal;

    /**
     * constructor- gets 3 points and creates a plane
     *
     * @param p1
     * @param p2
     * @param p3
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        q0 = p1;
        Vector U = p2.subtract(p1);
        Vector V = p3.subtract(p1);
        Vector N = U.crossProduct(V);
        N.normalize();
        normal = N;
    }

    /**
     * constructor- gets a point and a vector and creates a plane
     *
     * @param p0
     * @param normal
     */
    public Plane(Point3D p0, Vector normal) {
        q0 = p0;
        this.normal = normal.normalized();
    }

    public Point3D getQ0() {
        return q0;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "q0=" + q0 +
                ", normal=" + normal +
                '}';
    }

    @Override
    public Vector getNormal(Point3D point) {
        return normal;
    }

}
