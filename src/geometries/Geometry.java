package geometries;

import elements.Material;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Geometry class represent the basic class of the geometries
 *
 * @author Chani & Sara Lea
 */
public abstract class Geometry implements Intersectable {
    /**
     * the ability of an object to return a color
     */
    protected Color _emission = Color.BLACK;
    /**
     * the material of the object
     */
    protected Material _material = new Material();

    /**
     * return the material
     * @return the material
     */
    public Material getMaterial() {
        return _material;
    }

    /**
     * changing the material
     * @param material the new material
     * @return the geometry object
     */
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

    /**
     * return the emission
     * @return the emission
     */
    public Color getEmission() {
        return _emission;
    }

    /**
     * calculate the normal of a geometry
     * @param point the location of the geometry
     * @return the normal vector
     */
    public abstract Vector getNormal(Point3D point);
}
