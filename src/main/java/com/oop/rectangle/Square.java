package com.oop.rectangle;

public class Square extends Rectangle {

    public Square(double side) {
        super(side, side);
        if (side <= 0) {
            throw new IllegalArgumentException("Side must be positive");
        }
    }

    @Override
    public String toString() {
        return "Square{}";
    }
}
