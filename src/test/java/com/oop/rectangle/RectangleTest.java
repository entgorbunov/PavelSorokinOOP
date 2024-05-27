package com.oop.rectangle;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RectangleTest {

    @Test
    void testGetArea() {
        Rectangle rectangle = new Rectangle(10, 20);
        assertEquals(200, rectangle.getArea(), "Area should be width multiplied by height");
    }

    @Test
    void testGetPerimeter() {
        Rectangle rectangle = new Rectangle(10, 20);
        assertEquals(60, rectangle.getPerimeter(), "Perimeter should be 2 times the sum of width and height");
    }
}
