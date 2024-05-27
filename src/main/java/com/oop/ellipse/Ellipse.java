package com.oop.ellipse;

import com.oop.ancestor.Shape;

import java.util.Objects;

public class Ellipse extends Shape {

    private final double semiMajorAxis;
    private final double semiMinorAxis;

    public Ellipse(double semiMajorAxis, double semiMinorAxis) {
        this.semiMajorAxis = semiMajorAxis;
        this.semiMinorAxis = semiMinorAxis;
    }

    @Override
    public double getArea() {
        return Math.PI * semiMajorAxis * semiMinorAxis;
    }

    @Override
    public double getPerimeter() {
        double a = semiMajorAxis;
        double b = semiMinorAxis;
        return Math.PI * (3 * (a + b) - Math.sqrt((3 * a + b) * (a + 3 * b)));
    }

    public double getSemiMajorAxis() {
        return semiMajorAxis;
    }

    public double getSemiMinorAxis() {
        return semiMinorAxis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ellipse ellipse = (Ellipse) o;
        return Double.compare(semiMajorAxis, ellipse.semiMajorAxis) == 0 && Double.compare(semiMinorAxis, ellipse.semiMinorAxis) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(semiMajorAxis, semiMinorAxis);
    }

    @Override
    public String toString() {
        return "Ellipse{" +
               "semiMajorAxis=" + semiMajorAxis +
               ", semiMinorAxis=" + semiMinorAxis +
               '}';
    }
}
