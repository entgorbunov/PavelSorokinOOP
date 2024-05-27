package com.oop.ellipse;

public class Circle extends Ellipse {

    public Circle(double radius) {
        super(radius, radius);
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive");
        }
    }

    public Circle(double semiMajorAxis, double semiMinorAxis) {
        super(validateAxes(semiMajorAxis, semiMinorAxis), semiMinorAxis);
    }

    private static double validateAxes(double semiMajorAxis, double semiMinorAxis) {
        if (semiMajorAxis != semiMinorAxis) {
            throw new IllegalArgumentException("In a circle, the major and minor axes must be equal.");
        }
        return semiMajorAxis;
    }

    @Override
    public String toString() {
        return "Circle{}";
    }
}
