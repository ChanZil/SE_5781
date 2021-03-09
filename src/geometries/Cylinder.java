package geometries;

import primitives.Ray;

/**
 * Cylinder class represent the shape of cylinder
 *
 * @author Chani & Sara Lea
 */
public class Cylinder extends Tube{
    double height;

    /**
     * constructor- gets a ray and 2 numbers and creates a Cylinder (made of Tube)
     *
     * @param axisRay the ray of the cylinder
     * @param radius
     * @param height
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                ", axisRay=" + axisRay +
                ", radius=" + radius +
                "} " + super.toString();
    }
}
