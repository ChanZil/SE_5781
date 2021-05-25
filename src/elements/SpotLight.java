package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * SpotLight class represent a light source like point light with one difference- it has a direction
 *
 * @author Chani & Sara Lea
 */
public class SpotLight extends PointLight{

    private final Vector _dir; //the direction of the spot light

    /**
     * the constructor of spot light
     * @param intensity color- the intensity of the spot light
     * @param position point- the position of the spot light
     * @param dir vector- the direction of the spot light
     */
    public SpotLight(Color intensity, Point3D position, Vector dir) {
        super(intensity, position);
        _dir = dir.normalized();
    }

    /**
     * get a point on an object and find the direction of the spot light in this point
     * @param p the 3D point
     * @return color- the intensity of the spot light in the point
     */
    @Override
    public Color getIntensity(Point3D p) {
        double cosTheta = _dir.dotProduct(getL(p).normalized());
        Color intensity = super.getIntensity(p);
        return intensity.scale(Math.max(0, cosTheta));
    }
}
