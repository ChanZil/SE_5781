package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * PointLight class represent a light source that his intensity is stronger as long as the light source
 * is closer to the object. Has no direction- light to 360 degrees.
 *
 * @author Chani & Sara Lea
 */
public class PointLight extends Light implements LightSource{
    private final Point3D _position; //the position of the light source

    //the parameters that helps to calculate the intensity of the light in every point
    private double _Kc = 1.0;
    private double _Kl = 0;
    private double _Kq = 0;

    /**
     * the constructor of point light
     * @param intensity the intensity of the point light
     * @param position the position of the point light
     */
    protected PointLight(Color intensity, Point3D position) {
        super(intensity);
        _position = position;
    }

    /**
     * @return Kc
     */
    public double getKc() {
        return _Kc;
    }

    /**
     * @return Kl
     */
    public double getKl() {
        return _Kl;
    }

    /**
     * @return Kq
     */
    public double getKq() {
        return _Kq;
    }

    /**
     * change the value of Kc
     * @param kc double- the parameter
     * @return the PointLight
     */
    public PointLight setKc(double kc) {
        _Kc = kc;
        return this;
    }

    /**
     * change the value of Kl
     * @param kl double- the parameter
     * @return the PointLight
     */
    public PointLight setKl(double kl) {
        _Kl = kl;
        return this;
    }

    /**
     * change the value of Kq
     * @param kq double- the parameter
     * @return the PointLight
     */
    public PointLight setKq(double kq) {
        _Kq = kq;
        return this;
    }

    /**
     * get a point on an object and find the direction of the point light in this point
     * @param p the 3D point
     * @return the intensity of the point light in the point
     */
    @Override
    public Color getIntensity(Point3D p) {
        double d = p.distance(_position);
        double factor = 1d / (_Kc + _Kl * d + _Kq * d * d);
        return getIntensity().scale(factor);
    }

    /**
     * get a point on an object and find the direction of the point light in this point
     * @param p the 3D point
     * @return a vector of the direction of the point light in the point
     */
    @Override
    public Vector getL(Point3D p) {
        return p.subtract(_position).normalize();
    }
}