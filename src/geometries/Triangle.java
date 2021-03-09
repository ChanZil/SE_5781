package geometries;

import primitives.*;

/**
 * Triangle class represent the shape triangle
 *
 * @author Chani & Sara Lea
 */
public class Triangle extends Polygon{
    /**
     * constructor- gets 3 points and create a triangle (made of Polygon)
     *
     * @param p1
     * @param p2
     * @param p3
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        super(p1, p2, p3);
    }

    @Override
    public String toString() {
        return "Triangle{} " + super.toString();
    }
}
