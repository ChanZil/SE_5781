package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Plane implements Geometry{
    Point3D q0;
    Vector normal;

    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        q0 = p1;
        Vector U = p2.subtract(p1);
        Vector V = p3.subtract(p1);
        Vector N = U.crossProduct(V);
        N.normalize();
        normal = N.scale(-1);
    }

    public Plane(Point3D p0, Vector normal) {
        q0 = p0;
        this.normal = normal.normalized();
    }

    public Point3D getQ0() {
        return q0;
    }

    public Vector getNormal() {
        return normal;
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
