package com.example.huarongdao.model;

public class Coordinate {
    public int x,y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(Coordinate coordinate) {
        this.x = coordinate.x;
        this.y = coordinate.y;
    }

    public Coordinate set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Coordinate add(int x, int y) {
        return new Coordinate(this.x+x, this.y+y);
    }

    @Override
    public Object clone() {
        return new Coordinate(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj.getClass() == this.getClass()) {
            Coordinate coordinate = (Coordinate) obj;
            return coordinate.x == this.x && coordinate.y == this.y;
        }
        return false;
    }
}
