package com.oop.triangle;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TriangleTest {

    @Test
    void testGetArea() {
        Triangle triangle = new Triangle(3, 4, 5);
        double expectedArea = 6.0;
        assertEquals(expectedArea, triangle.getArea(), "Area should be calculated correctly using Heron's formula");
    }

    @Test
    void testGetPerimeter() {
        Triangle triangle = new Triangle(3, 4, 5);
        double expectedPerimeter = 12.0;
        assertEquals(expectedPerimeter, triangle.getPerimeter(), "Perimeter should be the sum of all sides");
    }
}
