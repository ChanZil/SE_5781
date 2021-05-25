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
public class Plane extends Geometry{
    Point3D q0; //a point on the plane
    Vector normal; //a vector normal to the plane

    /**
     * constructor- gets 3 points and creates a plane
     *
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point
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
     * @param p0 a point on the plane
     * @param normal the vector normal to the plane
     */
    public Plane(Point3D p0, Vector normal) {
        q0 = p0;
        this.normal = normal.normalized();
    }

    /**
     * @return q0
     */
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

    /**
     * calculate the vector normal of the plane
     * @param point the location of the plane
     * @return the vector normal of the plane
     */
    @Override
    public Vector getNormal(Point3D point) {
        return normal;
    }

    /**
     * find the intersections point with the ray and the plane in a certain distance
     * @param ray check the intersections between it and the Geometry shape
     * @param maxDistance the maximum distance between the light source and the plane
     * @return a list with geoPoints- the intersections point and the object (plane)
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
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
        //if the intersection point is in the certain distance
        if (t <= 0 || alignZero(t - maxDistance) > 0){
            return  null;
        }
        Point3D point = ray.getPoint(t); //find the intersection with the plane
        return List.of(new GeoPoint(this, point));
    }

}
