package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Tests for Vector
 *
 * @author Chani zilbershlag $ Sara Lea Amirs
 */
class VectorTest {

    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);
    Point3D p1 = new Point3D(1, 2, 3);

    @Test
    public void testConstructor() {
        // test zero vector- constructor
        try {
            new Vector(0, 0, 0);
            fail("ERROR: zero vector does not throw an exception");
        } catch (Exception e) {}
    }

    @Test
    void add() {
        //test sum of 2 vectors
        if (!Point3D.ZERO.equals(p1.add(new Vector(-1, -2, -3))))
            fail("ERROR: Point + Vector does not work correctly");
    }

    @Test
    void subtract() {
        //test subtraction of 2 vectors
        if (!new Vector(1, 1, 1).equals(new Point3D(2, 3, 4).subtract(p1)))
            fail("ERROR: Point - Point does not work correctly");
    }

    @Test
    void scale() {
        //test multiplication of a scale in vector
        assertEquals(new Vector(2,4,6),v1.scale(2));
    }

    @Test
    void dotProduct() {
        // test Dot-Product
        if (!isZero(v1.dotProduct(v3)))
            fail("ERROR: dotProduct() for orthogonal vectors is not zero");
        if (!isZero(v1.dotProduct(v2) + 28))
            fail("ERROR: dotProduct() wrong value");

    }

    @Test
    void crossProduct() {

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals( v1.length() * v3.length(), vr.length(), 0.00001,"crossProduct() wrong result length");

        // Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)),"crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v3)),"crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // test zero vector from cross-product of co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    void lengthSquared() {
        // test length squared.
        double result = v1.lengthSquared();
        assertTrue(isZero(result-14),"ERROR: lengthSquared() wrong value");

    }

    @Test
    void length() {
        // test length.
        if (!isZero(new Vector(0, 3, 4).length() - 5))
            fail("ERROR: length() wrong value");
        if (!isZero(v1.lengthSquared() - 14))
            fail("ERROR: lengthSquared() wrong value");
    }

    @Test
    void normalize() {
        // test vector normalization vs vector length and cross-product
        Vector v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(v.getHead());
        Vector vCopyNormalize = vCopy.normalize();
        if (vCopy != vCopyNormalize)
            fail("ERROR: normalize() function creates a new vector");
        if (!isZero(vCopyNormalize.length() - 1))
            fail("ERROR: normalize() result is not a unit vector");
        Vector u = v.normalized();
        if (u == v)
            fail("ERROR: normalizated() function does not create a new vector");
    }
}