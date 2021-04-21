package geometries;

import primitives.Point3D;
import primitives.Vector;

public interface Geometry extends Intersectable {
    /**
     *
     * @param point
     * @return
     */
    Vector getNormal(Point3D point);
}
