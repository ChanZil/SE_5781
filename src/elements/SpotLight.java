package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class SpotLight extends PointLight{

    private final Vector _dir;

    protected SpotLight(Color intensity, Point3D position, Vector dir) {
        super(intensity, position);
        _dir = dir;
    }

    @Override
    public Color getIntensity(Point3D p) {
        Vector l = getL(p);
        double cosTheta = _dir.dotProduct(l);
        return super.getIntensity(p).scale(Math.max(0, cosTheta));
    }
}
