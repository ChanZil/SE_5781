package primitives;

import java.util.Objects;

import static primitives.Point3D.ZERO;

public class Vector {
    Point3D head;

    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        if (head.equals(ZERO)) {
            throw new IllegalArgumentException("Vector head cannot be Point(0,0,0)");
        }
        head = new Point3D(x, y, z);
    }

    public Vector(double x, double y, double z) {
        Point3D point = new Point3D(x, y, z);
        if (point.equals(ZERO)) {
            throw new IllegalArgumentException("Vector head cannot be Point(0,0,0)");
        }
        head = new Point3D(x, y, z);
    }

    public Vector(Point3D head) {
        this.head = head;
    }

    public Point3D getHead() {
        return head;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return head.equals(vector.head);
    }

    @Override
    public String toString() {
        return "{" +  head + '}';
    }

    public Vector add(Vector v) {
        double x = head.x.coord + v.head.x.coord;
        double y = head.y.coord + v.head.y.coord;
        double z = head.z.coord + v.head.z.coord;
        return new Vector(new Point3D(x, y, z));
    }

    public Vector subtract(Vector v) {
        double x = head.x.coord - v.head.x.coord;
        double y = head.y.coord - v.head.y.coord;
        double z = head.z.coord - v.head.z.coord;
        return new Vector(new Point3D(x, y, z));
    }

    public Vector scale(double scalar) {
        return new Vector(new Point3D(
                        scalar * head.x.coord,
                        scalar * head.y.coord,
                        scalar * head.z.coord));
    }

    public double dotProduct(Vector v) {
        double u1 = head.x.coord;
        double u2 = head.y.coord;
        double u3 = head.z.coord;
        double v1 = v.head.x.coord;
        double v2 = v.head.y.coord;
        double v3 = v.head.z.coord;
        return (u1 * v1 + u2 * v2 + u3 * v3);
    }

    public Vector crossProduct(Vector v) {
        if (v.equals(ZERO)) {
            throw new IllegalArgumentException("Vector head cannot be Point(0,0,0)");
        }
        double u1 = head.x.coord;
        double u2 = head.y.coord;
        double u3 = head.z.coord;
        double v1 = v.head.x.coord;
        double v2 = v.head.y.coord;
        double v3 = v.head.z.coord;
        return new Vector(new Point3D(
                u2 * v3 - u3 * v2,
                u3 * v1 - u1 * v3,
                u1 * v2 - u2 * v1
        ));
    }

    public double lengthSquared() {
        return head.x.coord * head.x.coord + head.y.coord * head.y.coord + head.z.coord * head.z.coord;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vector normalize() {
        double length = this.length();
        //cannot divide by 0
        if (length == 0)
            throw new ArithmeticException("divide by Zero");
        double x = this.head.x.coord;
        double y = this.head.y.coord;
        double z = this.head.z.coord;
        this.head = new Point3D(x / length, y / length, z / length);
        return this;
    }

    public Vector normalized() {
        Vector vec = new Vector(head);
        vec.normalize();
        return vec;
    }

}
