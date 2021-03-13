package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Sphere
 *
 * @author Chani & Sara
 */
class SphereTest {

    @Test
    void getNormal() {
        Sphere sp = new Sphere(new Point3D(0,0,0), 5);
        assertEquals(new Vector(1,0,0),sp.getNormal(new Point3D(5,0,0)));
    }
}