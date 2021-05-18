package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

/**
 * Tube class represent the shape Tube
 *
 * @author Chani & Sara Lea
 */
public class Tube extends Geometry {
    Ray axisRay;
    double radius;

    /**
     * gets a ray and the radius of the tube and creates a Tube
     *
     * @param axisRay
     * @param radius  the radius of the tube
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

    /**
     * normalize Tube
     */
    @Override
    public Vector getNormal(Point3D point) {
        Point3D P0 = axisRay.getpO();
        Vector v = axisRay.getDir();
        Vector P0_P = point.subtract(P0);
        double t = alignZero(v.dotProduct(P0_P));
        if (isZero(t)) {
            return P0_P.normalize();
        }
        Point3D O = P0.add(v.scale(t));
        Vector n = point.subtract(O);
        return n.normalize();
    }

    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return null;
    }
}

