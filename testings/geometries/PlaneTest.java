package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Plane
 *
 * @author Chani & Sara
 */
class PlaneTest {

    @Test
    void getNormal() {
        Plane pl = new Plane(new Point3D(0,0,0), new Vector(2d,0d,0d));
        assertEquals(new Vector(1d,0d,0d),pl.getNormal(null));
    }
}