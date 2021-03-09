package primitives;

import java.util.Objects;

/**
 * Point3d class is the basic class representing a 3D point. Based on the Coordinate class.
 *
 * @author Chani & Sara Lea
 */

public class Point3D {
    // The 3 coordinates of the 3D point- x, y, z
    final Coordinate x;
    final Coordinate y;
    final Coordinate z;

    //The point (0,0,0)
    public final static Point3D ZERO = new Point3D(0, 0, 0);

    /**
     * constructor- gets 3 coordinates and create a Point3D
     *
     * @param x Coordinate for x
     * @param y Coordinate for y
     * @param z Coordinate for z
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this.x = new Coordinate(x.coord);
        this.y = new Coordinate(y.coord);
        this.z = new Coordinate(z.coord);
    }

    /**
     * constructor- gets 3 numbers and create 3 coordinates and then a Point3D
     *
     * @param x for coordinate x
     * @param y for coordinate y
     * @param z for coordinate z
     */
    public Point3D(double x, double y, double z) {
        this.x = new Coordinate(x);
        this.y = new Coordinate(y);
        this.z = new Coordinate(z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return x.equals(point3D.x) && y.equals(point3D.y) && z.equals(point3D.z);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + "," + z + ")";
    }

    /**
     * A subtraction from the current point to a point it gets
     *
     * @param newPoint the new point for the subtraction
     * @return new Vector of the subtraction
     */
    public Vector subtract(Point3D newPoint) {
        if (newPoint.equals(this)) {
            throw new IllegalArgumentException("cannot create Vector to Point(0,0,0)");
        }
        return  new Vector(new Point3D(
                x.coord - newPoint.x.coord,
                y.coord - newPoint.y.coord,
                z.coord - newPoint.z.coord
        ));
    }

    /**
     * Add a vector to the current point
     *
     * @param v the vector to add to the point
     * @return the point of the result
     */
    public Point3D add(Vector v) {
        return new Point3D(
                this.x.coord + v.head.x.coord,
                this.y.coord + v.head.y.coord,
                this.z.coord + v.head.z.coord);
    }

    /**
     * Calculates the distance between 2 points squared.
     *
     * @param point the second point to calculate the distance
     * @return the result- the distance
     */
    public double distanceSquared(Point3D point) {
        return ((point.x.coord - x.coord) * (point.x.coord - x.coord))
                + ((point.y.coord - y.coord) * (point.y.coord - y.coord))
                + ((point.z.coord - z.coord) * (point.z.coord - z.coord));
    }

    /**
     * Calculates the distance between 2 points.
     *
     * @param point the second point to calculate the distance
     * @return he result- the distance
     */
    public double distance(Point3D point) {
        return Math.sqrt(distanceSquared(point));
    }

}
