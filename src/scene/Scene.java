package scene;

import elements.AmbientLight;
import geometries.Geometries;
import primitives.Color;

/**
 * Scene class represent the scene
 *
 * @author Chani & Sara Lea
 */
public class Scene {
    public String name;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight = new AmbientLight(Color.BLACK, 0);
    public Geometries geometries = new Geometries();

    /**
     * the main constructor
     * @param name the name of the scene
     */
    public Scene(String name) {
        this.name = name;
    }

    /**
     * set the background color of the scene
     * @param background the new background
     * @return the scene
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * set the ambientLight of the scene
     * @param ambientLight the new ambientLight
     * @return the scene
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * set the geometries list of the scene
     * @param geometries the new geometries of the scene
     * @return the scene
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}
