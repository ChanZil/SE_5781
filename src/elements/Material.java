package elements;

public class Material {
    public double Kd = 0;
    public double Ks = 0;
    public int nShininess = 0;

    public double getKd() {
        return Kd;
    }

    public Material setKd(double kd) {
        Kd = kd;
        return this;
    }

    public double getKs() {
        return Ks;
    }

    public Material setKs(double ks) {
        Ks = ks;
        return this;
    }

    public int getShininess() {
        return nShininess;
    }

    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
