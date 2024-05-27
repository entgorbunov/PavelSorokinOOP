package com.oop.triangle;

public class RegularTriangle extends Triangle {

    public RegularTriangle(double side) {
        super(side, side, side);
    }

    @Override
    public String toString() {
        return "RegularTriangle{}";
    }
}
