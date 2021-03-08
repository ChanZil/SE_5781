package primitives;

import java.util.Objects;

public class Point3D {
    final Coordinate x;
    final Coordinate y;
    final Coordinate z;

    public final static Point3D ZERO = new Point3D(0, 0, 0);

    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this.x = new Coordinate(x.coord);
        this.y = new Coordinate(y.coord);
        this.z = new Coordinate(z.coord);
    }

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

    public Point3D add(Vector v) {
        return new Point3D(
                this.x.coord + v.head.x.coord,
                this.y.coord + v.head.y.coord,
                this.z.coord + v.head.z.coord);
    }

    public double distanceSquared(Point3D point) {
        return ((point.x.coord - x.coord) * (point.x.coord - x.coord))
                + ((point.y.coord - y.coord) * (point.y.coord - y.coord))
                + ((point.z.coord - z.coord) * (point.z.coord - z.coord));
    }

    public double distance(Point3D point) {
        return Math.sqrt(distanceSquared(point));
    }

}
