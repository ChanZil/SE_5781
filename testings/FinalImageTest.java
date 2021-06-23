import elements.*;
import geometries.Geometry;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import renderer.BasicRayTracer;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

/**
 * FinalImageTest class represent the final image of the project
 *
 * @author Chani & Sara Lea
 */
public class FinalImageTest {
    private Scene scene = new Scene("final image scene")
            .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

    private Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setViewPlaneSize(200, 200)
            .setDistance(1000)
            .setBeamOfRay(81);

    private static Geometry sphere1 = new Sphere(50, new Point3D(0, 0, 0))
            .setEmission(new Color(java.awt.Color.BLACK))
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100).setKt(0.1).setKr(1));
    private static Geometry sphere2 = new Sphere(80, new Point3D(0, 0, 0))
            .setEmission(new Color(java.awt.Color.BLUE))
            .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(1).setKr(0));
    private static Geometry sphere3 = new Sphere(40, new Point3D(-75, -75, 0))
            .setEmission(new Color(java.awt.Color.YELLOW))
            .setMaterial(new Material().setKt(0));
    private static Geometry sphere4 = new Sphere(20, new Point3D(-75, -40, 0))
            .setEmission(new Color(java.awt.Color.GREEN))
            .setMaterial(new Material().setKt(0.5));
    private static Geometry sphere5 = new Sphere(10, new Point3D(-80, 20, 0))
            .setEmission(new Color(java.awt.Color.YELLOW))
            .setMaterial(new Material().setKt(0.5));
    private static Geometry sphere6 = new Sphere(25, new Point3D(-100, 50, 0))
            .setEmission(new Color(java.awt.Color.WHITE))
            .setMaterial(new Material().setKt(0));
    private static Geometry sphere7 = new Sphere(40, new Point3D(-30, 100, 0))
            .setEmission(new Color(java.awt.Color.CYAN))
            .setMaterial(new Material().setKt(1));

    private static Geometry triangle1 = new Triangle(
            new Point3D(75, 150, -150), new Point3D(150, 75, -150), new Point3D(0, 0, 0))
            .setEmission(new Color(java.awt.Color.GREEN))
            .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(50).setKt(1));
    private static Geometry triangle2 = new Triangle(
            new Point3D(200, 50, -150), new Point3D(200, 0, -150), new Point3D(0, 0, 0))
            .setEmission(new Color(java.awt.Color.GREEN))
            .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(50).setKt(0.8));
    private static Geometry triangle3 = new Triangle(
            new Point3D(200, -20, -150), new Point3D(200, -200, -150), new Point3D(0, 0, 0))
            .setEmission(new Color(java.awt.Color.GREEN))
            .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(50).setKt(0.4));
    private static Geometry triangle4 = new Triangle(
            new Point3D(180, -200, -150), new Point3D(50, -200, -150), new Point3D(0, 0, 0))
            .setEmission(new Color(java.awt.Color.GREEN))
            .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(50).setKt(0));


    /**
     * run the test of the final image
     */
    @Test
    public void TestFinalImage(){
        //adding geometries to the scene
        scene.geometries.add(sphere1, sphere2, sphere3, sphere4, sphere5, sphere6, sphere7,
                triangle1, triangle2, triangle3, triangle4);
        //adding lights to the scene
        scene.lights.add(new SpotLight(new Color(java.awt.Color.WHITE), new Point3D(0, 0, 0), new Vector(1, 1, 0)));
        scene.lights.add(new SpotLight(new Color(java.awt.Color.WHITE), new Point3D(0, 0, 0), new Vector(1, -1, 0)));
        scene.lights.add(new SpotLight(new Color(java.awt.Color.WHITE), new Point3D(0, 0, 0), new Vector(-1, 1, 0)));
        scene.lights.add(new SpotLight(new Color(java.awt.Color.WHITE), new Point3D(0, 0, 0), new Vector(-1, -1, 0)));
        //write the image of the scene
        ImageWriter imageWriter = new ImageWriter("Final Image", 500, 500);
        //rendering the scene
        Render render = new Render()
                .setImageWriter(imageWriter)
                .setCamera(camera)
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImageSuperSampling();
        render.writeToImage();
    }
}
