package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Tube
 *
 * @author Chani & Sara
 */
class TubeTest {

    @Test
    void getNormal() {
        Tube tb = new Tube(new Ray(new Point3D(0,0,0), new Vector(1, 2, 5)), 5);
        assertEquals(new Vector(1,0,0),tb.getNormal(new Point3D(5,0,0)));
    }
}