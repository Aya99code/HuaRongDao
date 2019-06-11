package com.example.huarongdao.model;

import android.widget.Button;

public class Movement {

    public final int pieceIndex;
    public Coordinate from, to;
    public final int direction;
    public int step;

    @Override
    public boolean equals(Object obj) {
        if(this==obj){
            return true;
        }
        if(!(obj instanceof Movement)){
            return false;
        }
        Movement m = (Movement) obj;
        return from.equals(m.from) && to.equals(m.to) && pieceIndex == m.pieceIndex && step == m.step && direction == m.direction;
    }

    public Movement(Coordinate from, Coordinate to, int index, int direction, int step) {
        this.from = from;
        this.to = to;
        this.pieceIndex = index;
        this.direction = direction;
        this.step = step;
    }

    public Movement invert() {
        Coordinate temp = this.from;
        this.from = this.to;
        this.to = temp;
        int direction = this.direction;
        switch (direction) {
            case MOVE_UP:
                direction = MOVE_DOWN;
                break;
            case MOVE_DOWN:
                direction = MOVE_UP;
                break;
            case MOVE_LEFT:
                direction = MOVE_RIGHT;
                break;
            case MOVE_RIGHT:
                direction = MOVE_RIGHT;
                break;
        }
        return this;
    }

    public static final int MOVE_UP = 0;
    public static final int MOVE_DOWN = 1;
    public static final int MOVE_LEFT = 2;
    public static final int MOVE_RIGHT = 3;
}
