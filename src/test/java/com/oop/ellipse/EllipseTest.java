package com.oop.ellipse;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EllipseTest {

    @Test
    void testGetArea() {
        Ellipse ellipse = new Ellipse(10, 20);
        assertEquals(Math.PI * 10 * 20, ellipse.getArea(), "Area calculation should match formula");
    }

    @Test
    void testGetPerimeter() {
        Ellipse ellipse = new Ellipse(10, 20);
        double a = 10;
        double b = 20;
        double expectedPerimeter = Math.PI * (3 * (a + b) - Math.sqrt((3 * a + b) * (a + 3 * b)));
        assertEquals(expectedPerimeter, ellipse.getPerimeter(), "Perimeter calculation should match Ramanujan's approximation");
    }


}
