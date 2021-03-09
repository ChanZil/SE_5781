package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Tests for Vector
 *
 * @author Chani
 */
class VectorTest {

    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);

    @Test
    void add() {
    }

    @Test
    void subtract() {
    }

    @Test
    void scale() {
    }

    @Test
    void dotProduct() {

        assertEquals(-28,v1.dotProduct(v2));
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
        // test zero vector from cross-productof co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    void lengthSquared() {
//        if (!isZero(v1.lengthSquared() - 14))
//            fail("ERROR: lengthSquared() wrong value");
        double result = v1.lengthSquared();
        assertTrue(isZero(result-14),"ERROR: lengthSquared() wrong value");

    }

    @Test
    void length() {
        // test length..
        if (!isZero(new Vector(0, 3, 4).length() - 5))
            fail("ERROR: length() wrong value");

    }

    @Test
    void normalize() {
    }

    @Test
    void normalized() {
    }
}