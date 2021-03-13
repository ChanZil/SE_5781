package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Triangle
 *
 * @author Chani & Sara
 */
class TriangleTest {
    @Test
    void getNormal(){
        Triangle tr = new Triangle(new Point3D(0,0,1),new Point3D(1,1,1),new Point3D(1,0,1));
        assertEquals(new Vector(0d,0d,-1d),tr.getNormal(null));
    }

}