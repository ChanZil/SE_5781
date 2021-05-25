package elements;

/**
 * Material class represent the material of the object
 *
 * @author Chani & Sara Lea
 */
public class Material {
    //the attenuation parameters:
    public double Kd = 0;
    public double Ks = 0;
    //the shininess parameter:
    public int nShininess = 0;

    public double kt = 0d;  // opaque [0.0 - 1.0]
    public double kr = 0d;  // matt surface [0.0 - 1.0]

    public double getKt() {
        return kt;
    }

    public double getKr() {
        return kr;
    }

    ///chaining setter methods
    public Material setKt(double kt) {
        this.kt = kt;
        return this;
    }

    public Material setKr(double kr) {
        this.kr = kr;
        return this;
    }

    /**
     * @return Kd
     */
    public double getKd() {
        return Kd;
    }

    /**
     * change the value of Kd
     *
     * @param kd the new parameter
     * @return the material
     */
    public Material setKd(double kd) {
        Kd = kd;
        return this;
    }

    /**
     * @return Ks
     */
    public double getKs() {
        return Ks;
    }

    /**
     * change the value of Ks
     *
     * @param ks the new parameter
     * @return the material
     */
    public Material setKs(double ks) {
        Ks = ks;
        return this;
    }

    /**
     * @return nShininess
     */
    public int getShininess() {
        return nShininess;
    }

    /**
     * change the value of nShininess
     *
     * @param nShininess the new parameter
     * @return the material
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
