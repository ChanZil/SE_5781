package geometries;

import elements.Material;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 *
 */
public abstract class Geometry implements Intersectable {
    /**
     *
     */
    protected Color _emission = Color.BLACK;
    protected Material _material = new Material();

    public Material getMaterial() {
        return _material;
    }

    public Geometry setMaterial(Material material) {
        _material = material;
        return this;
    }

    /**
     * chaining setter method for the emission
     * @param emission emission color inherent to the current geometry
     * @return this: the current geometry object
     */
    public Geometry setEmission(Color emission) {
        _emission = emission;
        return this;
    }

    public Color getEmission() {
        return _emission;
    }

    /**
     *
     * @param point
     * @return
     */
    public abstract Vector getNormal(Point3D point);
}
