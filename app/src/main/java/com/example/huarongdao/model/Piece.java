package com.example.huarongdao.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Piece {

    public static final ArrayList<String> PIECES_NAME = new ArrayList<>(Arrays.asList("", "兵", "关羽", "马超", "黄忠", "张飞", "赵云", "曹操"));
    public static final int VerticalDelta[] = { 0, 0, 0, 1, 1, };
    public static final int HorizonDelta[] = { 0, 0, 1, 0, 1, };

    public final Shape shape;
    public final String name;
    int top, left;

    Piece(Shape s, int left, int top, String name) {
        this.shape = s;
        this.top = top;
        this.left = left;
        this.name = name;
    }

    Piece(Piece piece) {
        this.shape = piece.shape;
        this.top = piece.top;
        this.left = piece.left;
        this.name = piece.name;
    }

    public void setPosition() {

    }

    public int top() {
        return this.top;
    }

    public int left() {
        return this.left;
    }

    public int right() {
        return this.left + Piece.HorizonDelta[this.shape.ordinal()];
    }

    public int bottom() {
        return this.top + Piece.VerticalDelta[this.shape.ordinal()];
    }

    @Override
    public Object clone() {
        return new Piece(shape, left, top, name);
    }

    public int getShapeIndex() {
        return this.shape.ordinal();
    }


    public static int createDataOfPiece(String name, int shapeIndex, int x, int y) {
        // put all message (include position, shape.ordinal, name index) into a int number
        // for save in file
        int n = PIECES_NAME.indexOf(name);
        n = (((((n << 4) + shapeIndex) << 4) + x) << 4) + y;
        return n;
    }

    public static int createDataOfPiece(Piece piece) {
        // put all message (include position, shape.ordinal, name index) into a int number
        // for save in file
        int n = PIECES_NAME.indexOf(piece.name);
        return (((((n << 4) + piece.shape.ordinal()) << 4) + piece.left) << 4) + piece.top;
    }

    public static Piece getPieceFrom(final int data) {
        String name = PIECES_NAME.get(data >> 12);
        int shapeIndex = (data >> 8) - ((data >> 12) << 4);
        int left = (data >> 4) - ((data >> 8) << 4);
        int top = data - ((data >> 4) << 4);
        Shape shape = Shape.values()[shapeIndex];
        return new Piece(shape, left, top, name);
    }

    public static int createTypeDataOfPiece(String name, int shapeIndex, int index) {
        // put name, index in pieces, type into a int
        // for calculate the solution easily
        int n = PIECES_NAME.indexOf(name);
        return ((((n << 4) + index) << 4) + shapeIndex);
    }

    public static int createTypeDataOfPiece(Piece piece, int index) {
        // put name, index in pieces, type into a int
        // for calculate the solution easily
        int n = PIECES_NAME.indexOf(piece.name);
        return ((((n << 4) + index) << 4) + piece.shape.ordinal());
    }
}

