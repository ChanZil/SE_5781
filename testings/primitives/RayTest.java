package primitives;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test Ray class
 *
 * @author Chani & Sara Lea
 */
class RayTest {

    /**
     * test the function findClosestPoint
     */
    @Test
    void findClosestPoint() {
        Ray ray = new Ray(new Point3D(0, 0, 1), new Vector(0, 0, 1));
        Point3D p1 = new Point3D(0, 0, 2);
        Point3D p2 = new Point3D(0, 1, 3);
        Point3D p3 = new Point3D(5, 7, 9);
        Point3D p4 = new Point3D(12, 30, 8);
        // ============ Equivalence Partitions Tests ==============

        //TC01: the closest point in the middle of the list
        List<Point3D> pointsList = new LinkedList<Point3D>();
        pointsList.add(p2);
        pointsList.add(p3);
        pointsList.add(p1);
        pointsList.add(p4);
        assertEquals(pointsList.get(2), ray.findClosestPoint(pointsList), "Bad point");

        // =============== Boundary Values Tests ==================

        //TC11: the list is empty
        pointsList = null;
        assertNull(ray.findClosestPoint(pointsList), "Bad point");

        //TC12: the closest point is the first in the list
        pointsList = new LinkedList<Point3D>();
        pointsList.add(p1);
        pointsList.add(p3);
        pointsList.add(p2);
        pointsList.add(p4);
        assertEquals(pointsList.get(0), ray.findClosestPoint(pointsList), "Bad point");

        //TC13: the closest point is the last in the list
        pointsList = new LinkedList<Point3D>();
        pointsList.add(p4);
        pointsList.add(p3);
        pointsList.add(p2);
        pointsList.add(p1);
        assertEquals(pointsList.get(3), ray.findClosestPoint(pointsList), "Bad point");
    }
}