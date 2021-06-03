package elements;

import primitives.Color;

/**
 * Light class represent the intensity of light in a scene
 *
 * @author Chani & Sara Lea
 */
abstract class Light {
    protected final Color _intensity; //the intensity of the light

    /**
     * the constructor of Light
     * @param intensity the intensity of the light
     */
    protected Light(Color intensity) {
        _intensity = intensity;
    }

    /**
     * @return the intensity of the light
     */
    public Color getIntensity() {
        return _intensity;
    }
}
