package primitives;

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
    private static final double DELTA = 0.1;

    private final Point3D _pO; //the point that the ray start
    private final Vector _dir; //the direction of the ray

    /**
     * constructor- gets a Point3D and a Vector and creates a Ray
     *
     * @param pO  the point of the ray
     * @param dir the vector of the ray
     */
    public Ray(Point3D pO, Vector dir) {
        _pO = pO;
        _dir = dir.normalized();
    }

    public Ray(Point3D point, Vector dir, Vector n) {
        Vector delta = n.scale(n.dotProduct(dir) > 0 ? DELTA : -DELTA);
        Point3D p = point.add(delta);
        _pO = p;
        _dir = dir;
    }

    /**
     * @return the start point of the ray
     */
    public Point3D getpO() {
        return _pO;
    }

    /**
     * @return the direction of the ray
     */
    public Vector getDir() {
        return _dir;
    }

    /**
     * Get delta and multiply the ray in it and return it.
     *
     * @param delta the number to multiply
     * @return the new ray
     */
    public Point3D getPoint(double delta) {
        if (isZero(delta)) {
            return _pO;
        }
        return _pO.add(_dir.scale(delta));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return _pO.equals(ray._pO) && _dir.equals(ray._dir);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_pO, _dir);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "pO=" + _pO +
                ", dir=" + _dir +
                '}';
    }

    /**
     * get a list of points and find the point with the closest distance with the ray
     *
     * @param pointsList the list of the points
     * @return the closest point
     */
    public Point3D findClosestPoint(List<Point3D> pointsList) {
        Point3D closestPoint = null;
        double closestDistance = Double.MAX_VALUE;
        //if the list is empty
        if (pointsList == null)
            return null;
        //pass the list of the point
        for (Point3D pointer : pointsList)
            //if the distance of the point is smaller then the distance of the previous point- save the new point
            if (pointer.distance(_pO) < closestDistance) {
                closestDistance = pointer.distance(_pO);
                closestPoint = pointer;
            }
        return closestPoint;
    }

    /**
     * get a list of GeoPoints and find the GeoPoint with the closest distance with the ray
     *
     * @param intersections the list of GeoPoints
     * @return the closest GeoPoint to the ray
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections) {
        GeoPoint closestPoint = null;
        double closestDistance = Double.MAX_VALUE;
        //if the list is empty
        if (intersections == null)
            return null;
        //pass the list of the point
        for (GeoPoint gp : intersections) {
            //if the distance of the point is smaller then the distance of the previous point- save the new point
            double distance = gp.point.distance(_pO);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestPoint = gp;
            }
        }
        return closestPoint;
    }
}
