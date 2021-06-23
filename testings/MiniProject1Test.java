import elements.AmbientLight;
import elements.Camera;
import elements.Material;
import elements.SpotLight;
import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import renderer.BasicRayTracer;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

/**
 * MiniProject1Test class represent the image test of mini project 1- super sampling.
 *
 * @author Chani & Sara Lea
 */
public class MiniProject1Test {
    private Scene scene = new Scene("Test scene")
            .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

    private Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setViewPlaneSize(200, 200)
            .setDistance(1000)
            .setBeamOfRay(81);

    private static Geometry triangle1 = new Triangle(
            new Point3D(-150, -150, -150), new Point3D(150, -150, -150), new Point3D(150, 150, -150));

    /**
     * test mini project 1- super sampling
     */
    @Test
    public void TestMiniProject1() {
        //adding geometries to the scene
        scene.geometries.add(triangle1.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)),
                new Sphere(70, new Point3D(0, 0, -50))
                        .setEmission(new Color(java.awt.Color.GREEN))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(25, new Point3D(0, 0, -50))
                        .setEmission(new Color(java.awt.Color.WHITE))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),
                new Sphere(30, new Point3D(50, 0, 0))
                        .setEmission(new Color(java.awt.Color.BLACK))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(50).setKt(0.3)),
                new Sphere(40, new Point3D(70, 0, 0))
                        .setEmission(new Color(java.awt.Color.CYAN))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(20, new Point3D(-50, 0, 0))
                        .setEmission(new Color(java.awt.Color.CYAN))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(15, new Point3D(-70, 0, 0))
                        .setEmission(new Color(java.awt.Color.BLACK))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Triangle( //black
                        new Point3D(-150, 150, -150), new Point3D(150, 150, 150), new Point3D(0, 0, 150))
                        .setEmission(new Color(java.awt.Color.BLACK))
                .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)));

        //adding lights to the scene
        scene.lights.add(new SpotLight(new Color(500, 250, 250), new Point3D(10, -10, -130), new Vector(-2, -2, -1))
                .setKl(0.0001).setKq(0.000005));
        scene.lights.add( new SpotLight(new Color(500, 600, 0),new Point3D(-100, -100, 500),  new Vector(-1, -1, -2))
                .setKl(0.0004).setKq(0.0000006));

        //write the image of the scene
        ImageWriter imageWriter = new ImageWriter("MiniProject1", 500, 500);
        //rendering the scene
        Render render = new Render()
                .setImageWriter(imageWriter)
                .setCamera(camera)
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImageSuperSampling();
        render.writeToImage();
    }
}
