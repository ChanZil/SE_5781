package elements;

import primitives.Color;

/**
 * AmbientLight class represent the ambient light of a scene
 *
 * @author Chani & Sara Lea
 */
public class AmbientLight extends Light {

    /**
     * main constructor- restart the intensity to be black.
     */
    public AmbientLight() {
        super(Color.BLACK);
    }

    /**
     * create an ambient light
     * @param Ia the color of the ambient light
     * @param Ka the intensity of the ambient light
     */
    public AmbientLight(Color Ia, double Ka) {
         super(Ia.scale(Ka));
    }

}
