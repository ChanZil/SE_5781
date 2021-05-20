package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * LightSource interface represent the source of the 3 kinds of lights
 *
 * @author Chani & Sara Lea
 */
public interface LightSource {
    /**
     * get a point on an object and find the intensity of the light in this point
     * @param p the 3D point
     * @return color- the intensity of the light in the point
     */
    public Color getIntensity(Point3D p);

    /**
     * get a point on an object and find the direction of the light in this point
     * @param p the 3D point
     * @return a vector of the direction of the light in the point
     */
    public Vector getL(Point3D p);

}
