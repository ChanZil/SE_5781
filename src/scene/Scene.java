package scene;

import elements.AmbientLight;
import elements.LightSource;
import geometries.Geometries;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * Scene class represent the scene
 *
 * @author Chani & Sara Lea
 */
public class Scene {
    private final String _name; //the name of the scene
    public Color background = Color.BLACK; //the color background of the scene
    public AmbientLight ambientLight = new AmbientLight(Color.BLACK, 1); //the ambient light of the scene
    public Geometries geometries = new Geometries(); //the geometries participate in the scene
    public List<LightSource> lights = new LinkedList<>(); //all the lights participate in the scene

    /**
     * the main constructor
     * @param name the name of the scene
     */
    public Scene(String name) {
        _name = name;
    }

    /**
     * change the list of the lights participate in the scene
     * @param lights the new list of lights
     * @return the scene
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
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
