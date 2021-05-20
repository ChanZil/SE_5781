package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * DirectionalLight class represent a light source that his intensity of the object doesn't depend
 * on the distance between the light source and the object
 *
 * @author Chani & Sara Lea
 */
public class DirectionalLight extends Light implements LightSource  {
    private final Vector _direction; //the direction of the light

    /**
     * the constructor of directional light
     * @param intensity color- the intensity of the light (permanent)
     * @param direction vector- the direction of the light
     */
    protected DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        _direction = direction.normalized();
    }

    /**
     * get a point on an object and find the direction of the directional light in this point
     * @param p the 3D point
     * @return color- the intensity of the directional light in the point
     */
    @Override
    public Color getIntensity(Point3D p) {
        return super.getIntensity();
    }

    /**
     * get a point on an object and find the direction of the directional light in this point
     * @param p the 3D point
     * @return a vector of the direction of the directional light in the point
     */
    @Override
    public Vector getL(Point3D p) {
        return _direction;
    }
}
