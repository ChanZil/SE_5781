package primitives;

import java.util.Objects;

public class Ray {
    Point3D pO;
    Vector dir;

    public Ray(Point3D pO, Vector dir) {
        this.pO = pO;
        this.dir = dir.normalized();
    }

    public Point3D getpO() {
        return pO;
    }

    public Vector getDir() {
        return dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return pO.equals(ray.pO) && dir.equals(ray.dir);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pO, dir);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "pO=" + pO +
                ", dir=" + dir +
                '}';
    }
}
