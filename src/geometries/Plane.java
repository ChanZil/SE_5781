package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

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

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        Point3D P0 = ray.getpO();
        Vector v = ray.getDir();
        Vector n = normal;
        //if the ray starts from the point of the plane, there are no intersections.
        if(q0.equals(P0)){
            return  null;
        }
        Vector P0_Q0 = q0.subtract(P0);
        double nP0Q0  = alignZero(n.dotProduct(P0_Q0)); //numerator
        //if the numerator is zero, there are no intersections.
        if (isZero(nP0Q0)){
            return null;
        }
        double nv = alignZero(n.dotProduct(v)); //denominator
        //if the ray is lying in the plane axis- there are no intersections.
        if(isZero(nv)){
            return null;
        }
        double  t = alignZero(nP0Q0 / nv);
        if (t <= 0){
            return  null;
        }
        Point3D point = ray.getPoint(t); //find the intersection with the plane
        return List.of(point);
    }
}
