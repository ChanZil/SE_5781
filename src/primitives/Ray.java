package primitives;

import geometries.Intersectable;
import static geometries.Intersectable.GeoPoint;

import java.util.List;
import java.util.Objects;

import static primitives.Util.isZero;

/**
 * Ray class is the basic class representing a Ray. Based on a point and a vector.
 *
 * @author Chani & Sara Lea
 */
public class Ray {
    Point3D pO;
    Vector dir;

    /**
     * constructor- gets a Point3D and a Vector and creates a Ray
     *
     * @param pO the point of the ray
     * @param dir the vector of the ray
     */
    public Ray(Point3D pO, Vector dir) {
        this.pO = pO;
        this.dir = dir.normalized();
    }

    public Point3D getpO() {
        return pO;
    }

    public Vector getDir() {
        return dir;
    }

    /**
     * Get delta and multiply the ray in it and return it.
     *
     * @param delta the number to multiply
     * @return the new ray
     */
    public Point3D getPoint(double delta){
        if (isZero(delta)){
            return pO;
        }
        return pO.add(dir.scale(delta));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return pO.equals(ray.pO) && dir.equals(ray.dir);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pO, dir);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "pO=" + pO +
                ", dir=" + dir +
                '}';
    }

    /**
     * get a list of points anf find the point with the closest distance with the ray
     * @param pointsList the list of the points
     * @return the closest point
     */
    public Point3D findClosestPoint(List<Point3D> pointsList){
        Point3D closestPoint = null;
        double closestDistance = Double.MAX_VALUE;
        //if the list is empty
        if(pointsList == null)
            return null;
        //pass the list of the point
        for(Point3D pointer: pointsList)
            //if the distance of the point is smaller then the distance of the previous point- save the new point
            if(pointer.distance(pO) < closestDistance){
                closestDistance = pointer.distance(pO);
                closestPoint = pointer;
            }
        return closestPoint;
    }

    public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections) {
        GeoPoint closestPoint = null;
        double closestDistance = Double.MAX_VALUE;
        //if the list is empty
        if(intersections == null)
            return null;
        //pass the list of the point
        for(GeoPoint gp: intersections) {
            //if the distance of the point is smaller then the distance of the previous point- save the new point
            double distance = gp.point.distance(pO);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestPoint = gp;
            }
        }
        return closestPoint;
    }
}
