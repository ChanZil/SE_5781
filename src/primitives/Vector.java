package primitives;

import java.util.Objects;

import static primitives.Point3D.ZERO;

/**
 * Vector class is the basic class representing a vector.
 *
 * @author Chani & Sara Lea
 */
public class Vector {
    Point3D head;

    /**
     * constructor- gets 3 coordinates and creates a Vector
     *
     * @param x the coordinate x
     * @param y the coordinate y
     * @param z the coordinate z
     */
    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        this(new Point3D(x, y, z));
    }

    /**
     * constructor- gets 3 numbers and creates a Vector
     *
     * @param x the coordinate x
     * @param y the coordinate y
     * @param z the coordinate z
     */
    public Vector(double x, double y, double z) {
        this(new Point3D(x,y,z));
    }

    /**
     * mainly used constructor- gets the point of the head of the vector
     * @param head the head of the vector
     */
    public Vector(Point3D head) {
        if(head.equals(ZERO)){
            throw new IllegalArgumentException("Vector head cannot be Point(0,0,0)");
        }
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

    /**
     * Sum 2 Vectors
     *
     * @param v Vector to add to the current Vector
     * @return the result- the sum of the 2 vectors
     */
    public Vector add(Vector v) {
        double x = head.x.coord + v.head.x.coord;
        double y = head.y.coord + v.head.y.coord;
        double z = head.z.coord + v.head.z.coord;
        return new Vector(new Point3D(x, y, z));
    }

    /**
     * Subtract 2 Vectors
     *
     * @param v Vector to subtract from the current Vector
     * @return the result- the subtract of the 2 vectors
     */
    public Vector subtract(Vector v) {
        double x = head.x.coord - v.head.x.coord;
        double y = head.y.coord - v.head.y.coord;
        double z = head.z.coord - v.head.z.coord;
        return new Vector(new Point3D(x, y, z));
    }

    /**
     * Multiply the Vector in a scale
     *
     * @param scalar the scale to multiply the vector
     * @return new Vector based on the current vector multiplied by the scale
     */
    public Vector scale(double scalar) {
        return new Vector(new Point3D(
                        scalar * head.x.coord,
                        scalar * head.y.coord,
                        scalar * head.z.coord));
    }


    /**
     * Dot product between 2 Vectors
     *
     * @param v the second vector in the product
     * @return the result of the dot product
     */
    public double dotProduct(Vector v) {
        double u1 = head.x.coord;
        double u2 = head.y.coord;
        double u3 = head.z.coord;
        double v1 = v.head.x.coord;
        double v2 = v.head.y.coord;
        double v3 = v.head.z.coord;
        return (u1 * v1 + u2 * v2 + u3 * v3);
    }

    /**
     * Cross product between 2 Vectors
     *
     * @param v the second vector in the product
     * @return the result of the cross product
     */
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

    /**
     * Calculates the length of the Vector squared.
     *
     * @return the result of the calculation.
     */
    public double lengthSquared() {
        return head.x.coord * head.x.coord + head.y.coord * head.y.coord + head.z.coord * head.z.coord;
    }

    /**
     * Calculates the length of the Vector.
     *
     * @return the result of the calculation.
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Create a new Vector that normalized to the current Vector
     *
     * @return the new Vector
     */
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

    /**
     * Normalized the current Vector
     *
     * @return this
     */
    public Vector normalized() {
        Vector vec = new Vector(head);
        vec.normalize();
        return vec;
    }

}
