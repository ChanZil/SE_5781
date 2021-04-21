package geometries;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Testing Geometries
 *
 * @author Chani & Sara
 */
public class GeometriesTests {

    @Test
    void findIntersections() {
        Plane plane = new Plane(new Point3D(0, 0, 1), new Vector(1, 1, 1));
        Sphere sphere = new Sphere(new Point3D(1, 0, 0), 1d);
        Triangle tr = new Triangle(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        Geometries intersetables = new Geometries(plane, sphere, tr);
        Ray ray = new Ray(new Point3D(0.5, 0, 0), new Vector(1, 0, 0));
        // ============ Equivalence Partitions Tests ==============

        // TC01: the ray has some intersections with the geometries but not with all of them
        Plane plane1 = new Plane(new Point3D(0, 0, 1), new Vector(1, 1, 1));
        Triangle tr1 = new Triangle(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        Sphere sp1 = new Sphere(new Point3D(0.5,0.5,0.5), 1);
        Geometries intersetables0 = new Geometries(tr1);
        Ray ray0 = new Ray(new Point3D(1, 1, 1), new Vector(-1, -1, -1));
        assertEquals(List.of(new Point3D(1d / 3, 1d / 3, 1d / 3), new Point3D(2d / 3, 2d / 3, 2d / 3)), intersetables0.findIntersections(ray0), "Bad intersections");

        // =============== Boundary Values Tests ==================

        // TC11: the list of the intersetables is empty
        Geometries intersetables1 = new Geometries();
        assertNull(intersetables1.findIntersections(ray), "Not supposed to be intersections");
        // TC12: the ray has no intersections with the geometries

        Ray ray1 = new Ray(new Point3D(15, 15, 15), new Vector(1, 1, 1));
        assertNull(intersetables1.findIntersections(ray1), "Not supposed to be intersections");
        // TC13: the ray has intersection with only one geometry

        Ray ray2 = new Ray(new Point3D(-15, 0, 3), new Vector(1, 0, 0));
        assertEquals(List.of(new Point3D(-2.0, 0.0, 3.0)), intersetables.findIntersections(ray2), "Bad intersection");
        // TC14: the ray has intersections with all the geometries

        Plane plane2 = new Plane(new Point3D(0, 0, 1), new Vector(1, 1, 1));
        Triangle tr2 = new Triangle(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        Sphere sp2 = new Sphere(new Point3D(1,1,1), 1);
        Geometries intersetables2 = new Geometries(plane2, tr2, sp2);
        assertEquals(List.of(new Point3D(1d / 3, 1d / 3, 1d / 3), new Point3D(2d / 3, 2d / 3, 2d / 3), new Point3D(0.42264973081037416,0.42264973081037416,0.42264973081037416)), intersetables0.findIntersections(ray0), "Bad intersections");
    }
}
