package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Plane
 *
 * @author Chani & Sara
 */
class PlaneTest {

    @Test
    public void getNormal() {
        Plane pl = new Plane(new Point3D(0,0,0), new Vector(2d,0d,0d));
        assertEquals(new Vector(1d,0d,0d),pl.getNormal(null));
    }

    @Test
    public void testFindIntersectionsRay() {
        Plane plane = new Plane(new Point3D(0,0,1), new Vector(1,1,1));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray has intersections with plane
        assertEquals(List.of(new Point3D(1, 0, 0)),
                plane.findIntersections(new Ray(new Point3D(0.5, 0, 0), new Vector(1, 0, 0))),
                "Bad plane intersection");
        // TC02: Ray out of Plane
        assertNull(plane.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(1, 0, 0))),
                "Not intersection with the plane");
        // =============== Boundary Values Tests ==================
        // TC11: Ray parallel to plane
        assertNull(plane.findIntersections(new Ray(new Point3D(1, 1, 1), new Vector(0, 1, -1))),
                "Not intersection with the plane");

        // TC12: Ray in plane
        assertNull(plane.findIntersections(new Ray(new Point3D(0, 0.5, .5), new Vector(0, 1, -1))),
                "Not intersection with the plane");

        // TC13: Orthogonal ray into plane
        assertEquals(List.of(new Point3D(1d / 3, 1d / 3, 1d / 3)),
                plane.findIntersections(new Ray(new Point3D(1, 1, 1), new Vector(-1, -1, -1))),
                "Bad plane intersection");

        // TC14: Orthogonal ray out of plane
        assertNull(plane.findIntersections(new Ray(new Point3D(1, 1, 1), new Vector(1, 1, 1))),
                "Not intersection with the plane");

        // TC15: Orthogonal ray from plane
        assertNull(plane.findIntersections(new Ray(new Point3D(0, 0.5, 0.5), new Vector(1, 1, 1))),
                "Not intersection with the plane");

        // TC16: Ray from plane
        assertNull(plane.findIntersections(new Ray(new Point3D(0, 0.5, 0.5), new Vector(1, 1, 0))),
                "Not intersection with the plane");

        // TC17: Ray from plane's Q point
        assertNull(plane.findIntersections(new Ray(new Point3D(0, 0, 1), new Vector(1, 1, 0))),
                "Not intersection with the plane");
    }
}