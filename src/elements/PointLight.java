package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class PointLight extends Light implements LightSource{
    private final Point3D _position;

    private double _Kc = 1.0;
    private double _Kl = 0;
    private double _Kq = 0;

    protected PointLight(Color intensity, Point3D position) {
        super(intensity);
        _position = position;
    }

    public double getKc() {
        return _Kc;
    }

    public double getKl() {
        return _Kl;
    }

    public double getKq() {
        return _Kq;
    }

    public PointLight setKc(double kc) {
        _Kc = kc;
        return this;
    }

    public PointLight setKl(double kl) {
        _Kl = kl;
        return this;
    }

    public PointLight setKq(double kq) {
        _Kq = kq;
        return this;
    }

    @Override
    public Color getIntensity(Point3D p) {
        double d = p.distance(_position);
        double factor = 1d / (_Kc + _Kl * d + _Kq * d * d);
        return getIntensity().scale(factor);
    }

    @Override
    public Vector getL(Point3D p) {
        return p.subtract(_position).normalized();
    }
}
