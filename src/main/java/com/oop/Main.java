package com.oop;

import com.oop.ancestor.Shape;
import com.oop.ellipse.Circle;
import com.oop.ellipse.Ellipse;
import com.oop.rectangle.Rectangle;
import com.oop.rectangle.Square;
import com.oop.triangle.RegularTriangle;
import com.oop.triangle.Triangle;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Shape> shapes = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            shapes.add(new Ellipse(10 + i, 5 + i));
            shapes.add(new Circle(5 + i));
            shapes.add(new Rectangle(10 + i, 5 + i));
            shapes.add(new Square(5 + i));
            shapes.add(new Triangle(6, 8, 10));
            shapes.add(new RegularTriangle(10 + i));
        }

        double totalArea = 0;
        double totalPerimeter = 0;
        double totalTriangleArea = 0;
        double totalTrianglePerimeter = 0;
        double totalRectangleArea = 0;
        double totalRectanglePerimeter = 0;

        for (Shape shape : shapes) {
            totalArea += shape.getArea();
            totalPerimeter += shape.getPerimeter();

            if (shape instanceof Triangle) {
                totalTriangleArea += shape.getArea();
                totalTrianglePerimeter += shape.getPerimeter();
            }

            if (shape instanceof Rectangle) {
                totalRectangleArea += shape.getArea();
                totalRectanglePerimeter += shape.getPerimeter();
            }
        }

        System.out.println("Total Area of All Shapes: " + totalArea);
        System.out.println("Total Perimeter of All Shapes: " + totalPerimeter);
        System.out.println("Total Area of All Triangles: " + totalTriangleArea);
        System.out.println("Total Perimeter of All Triangles: " + totalTrianglePerimeter);
        System.out.println("Total Area of All Rectangles: " + totalRectangleArea);
        System.out.println("Total Perimeter of All Rectangles: " + totalRectanglePerimeter);
    }
}
