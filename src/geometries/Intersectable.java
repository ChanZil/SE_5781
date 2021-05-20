package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.stream.Collectors;

/**
 * An interface to find intersections of rays with geometries.
 *
 * @author Chani & Sara Lea
 */
public interface Intersectable {
    /**
     * GeoPoint class represent the intersection point with an object
     */
    public static class GeoPoint{
        public Geometry geometry; //the shape that the ray intersected with
        public Point3D point; //the intersection point with the ray and the object

        /**
         * basic constructor of GeoPoint
         * @param geometry the geometry shape
         * @param point
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }

        /**
         * check if 2 geoPoints are equal
         * @param o the other geoPoint to check
         * @return true if equal, false if not.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry.equals(geoPoint.geometry) && point.equals(geoPoint.point);
        }

    }
    /**
     * calculates all the intersections points of the ray given with the object
    * @param ray- check the intersections between it and the Geometry shape.
     * @return a list of all the intersections points.
    */
    default List<Point3D> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream()
                .map(gp -> gp.point)
                .collect(Collectors.toList());
    }

    /**
     * calculates all the intersections points of the ray given with the object
     * @param ray check the intersections between it and the Geometry shape
     * @return GeoPoint- a list of the geoPoints- including the intersections point and the object it intersected with.
     */
    public List<GeoPoint> findGeoIntersections(Ray ray);
}
