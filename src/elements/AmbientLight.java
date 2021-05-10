package elements;

import primitives.Color;

/**
 * AmbientLight class represent the ambient light of a scene
 *
 * @author Chani & Sara Lea
 */
public class AmbientLight {
    final private Color intensity;

    /**
     * main constructor- restart the intensity to be black.
     */
    public AmbientLight() {
        intensity = Color.BLACK;
    }

    /**
     * create an ambient light
     * @param Ia the color of the ambient light
     * @param Ka the intensity of the ambient light
     */
    public AmbientLight(Color Ia, double Ka) {
        this.intensity = Ia.scale(Ka);
    }

    /**
     * get the intensity of the ambient light
     * @return the intensity
     */
    public Color getIntensity() {
        return intensity;
    }
}
