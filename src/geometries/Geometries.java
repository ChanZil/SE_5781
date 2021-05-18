package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Geometries class represents a collection of geometries together
 *
 * @author Chani & Sara Lea
 */
public class Geometries implements Intersectable{
    private List<Intersectable> intersectables;

    /**
     * mainly used constructor- create a linkedList that will contain all the geometries.
     */
    public Geometries() {
        this.intersectables = new LinkedList<>();
    }

    /**
     * get geometries, create a linkedList with them
     * @param geos the geometries of the list.
     */
    public Geometries(Intersectable... geos) {
        this.intersectables = new LinkedList<>();
        add(geos);
    }

    /**
     * adds geometries to the list
     * @param geos the geometries to add
     */
    public void add(Intersectable... geos) {
        Collections.addAll(intersectables, geos);
    }

    public List<Point3D> findIntersections(Ray ray){
        List<Point3D> result = null;
        //passes the list of the intersectables and check for each geometry if it has intersections.
        for (Intersectable item : intersectables) {
            //get intersections points of a particular item from intersectables
            List<Point3D> itempoints = item.findIntersections(ray);
            //if the geometry has intersections with the ray
            if(itempoints != null){
                // if it the first time to find intersections- initialize result to new LinkedList
                if(result == null){
                    result = new LinkedList<>();
                }
                result.addAll(itempoints); //add all item points to the resulting list
            }
        }
        return result;
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        List<GeoPoint> result = null;
        //passes the list of the intersectables and check for each geometry if it has intersections.
        for (Intersectable item : intersectables) {
            //get intersections points of a particular item from intersectables
            List<GeoPoint> itempoints = item.findGeoIntersections(ray);
            //if the geometry has intersections with the ray
            if(itempoints != null){
                // if it the first time to find intersections- initialize result to new LinkedList
                if(result == null){
                    result = new LinkedList<>();
                }
                result.addAll(itempoints); //add all item points to the resulting list
            }
        }
        return result;
    }
}
