package primitives;

import java.util.Objects;

import static primitives.Util.isZero;

/**
 * Ray class is the basic class representing a Ray. Based on a point and a vector.
 *
 * @author Chani & Sara Lea
 */
public class Ray {
    Point3D pO;
    Vector dir;

    /**
     * constructor- gets a Point3D and a Vector and creates a Ray
     *
     * @param pO the point of the ray
     * @param dir the vector of the ray
     */
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

    /**
     * Get delta and multiply the ray in it and return it.
     *
     * @param delta the number to multiply
     * @return the new ray
     */
    public Point3D getPoint(double delta){
        if (isZero(delta)){
            return pO;
        }
        return pO.add(dir.scale(delta));
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
