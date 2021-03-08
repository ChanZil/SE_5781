package geometries;

import primitives.*;

public class Triangle extends Polygon{
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        super(p1, p2, p3);
    }

    @Override
    public String toString() {
        return "Triangle{} " + super.toString();
    }
}