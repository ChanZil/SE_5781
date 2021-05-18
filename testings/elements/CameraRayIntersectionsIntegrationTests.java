package elements;

import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.*;
import geometries.Intersectable;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * test the integration between ray creation from a camera to intersections of the ray
 * with geometries
 *
 * @author Chani & Sara Lea
 */
public class CameraRayIntersectionsIntegrationTests {
    Camera cam1 = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, 1), new Vector(0, 1, 0));
    Camera cam2 = new Camera(new Point3D(0, 0, 0.5), new Vector(0, 0, 1), new Vector(0, 1, 0));
    /**
     * integration test of creation of a ray from a camera and ray intersection with a triangle
     */
    @Test
    public void triangleTest(){
        //TC01: there is one intersection point
        Triangle tri1 = new Triangle(new Point3D(0, 1, -2), new Point3D(-1, -1, -2), new Point3D(1, -1, -2));
        assertEquals(1, amountOfIntersections(cam1, tri1), "Bad intersections");
        //TC01: there are two intersections point
        Triangle tri2 = new Triangle(new Point3D(0, 20, -2), new Point3D(-1, -1, -2), new Point3D(1, -1, -2));
        assertEquals(2, amountOfIntersections(cam1, tri2), "Bad intersections");
    }

    /**
     * integration test of creation of a ray from a camera and ray intersection with a plane
     */
    @Test
    public void planeTest(){
        //TC01: the plane is vertical to the camera -9 intersections
        Plane pl1 = new Plane(new Point3D(0, 0, 1), new Vector(0, 0, 1));
        assertEquals(9, amountOfIntersections(cam1, pl1), "Bad intersections");
        //TC02: the plane is against the camera with a small angle- 9 intersections
        Plane pl2 = new Plane(new Point3D(0, 0, 1), new Vector(0, 1, 2));
        assertEquals(9, amountOfIntersections(cam1, pl2), "Bad intersections");
        //TC03: the plane is against the camera with a big angle- 6 intersections
        Plane pl3 = new Plane(new Point3D(0, 0, 1), new Vector(0, 1, 1));
        assertEquals(6, amountOfIntersections(cam1, pl3), "Bad intersections");
    }

    /**
     * integration test of creation of a ray from a camera and ray intersection with a sphere
     */
    @Test
    public void sphereTest(){
        //TC01: 2 intersections
        Sphere sp1 = new Sphere(1, new Point3D(0, 0, -3));
        assertEquals(2, amountOfIntersections(cam1, sp1));
        //TC02: 18 intersections
        Sphere sp2 = new Sphere(2.5, new Point3D(0, 0, -2.5));
        assertEquals(18, amountOfIntersections(cam2, sp2));
        //TC03: 10 intersections
        Sphere sp3 = new Sphere(2, new Point3D(0, 0, -2));
        assertEquals(10, amountOfIntersections(cam2, sp3));
        //TC04: 9 intersections
        Sphere sp4 = new Sphere(4, new Point3D(0, 0, -1));
        assertEquals(9, amountOfIntersections(cam1, sp4));
        //TC05: no intersections at all
        Sphere sp5 = new Sphere(0.5, new Point3D(0, 0, 1));
        assertEquals(0, amountOfIntersections(cam1, sp5));
    }

    /**
     * find the amount of intersections with a geometry with a camera
     *
     * @param camera the camera of the scene- contains the rays and the view plane
     * @param geo the geometry in the scene
     * @return the amount of the intersections
     */
    private int amountOfIntersections(Camera camera, Intersectable geo){
        int amount = 0;
        camera.setViewPlaneSize(3, 3);
        camera.setDistance(1);
        //pass every pixel in the 3x3 view plane and find intersections of the ray with the geometry.
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                List<Point3D> intersections = geo.findIntersections(camera.constructRayThroughPixel(3, 3, j, i)); //find the intersections with the geometry with the ray of the current pixel
                //if found intersections
                if (intersections != null)
                     amount += intersections.size();
            }
        }
        return amount;
    }
}
