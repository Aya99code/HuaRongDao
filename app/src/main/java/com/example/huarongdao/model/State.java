package com.example.huarongdao.model;

import static com.example.huarongdao.MainActivity.height;
import static com.example.huarongdao.MainActivity.width;

public class State {

    public Piece[] pieces;
    int step;
    Piece square;
    Mask mask = null;

    State(Piece[] pieces, Piece square) {
        this.pieces = pieces;
        this.square = square;
    }

    State(Board board) {
        this.pieces = board.pieces;
        this.square = board.square;
        this.step = board.step;
    }

    @Override
    protected Object clone() {
        Piece[] newPieces = new Piece[pieces.length];
        Piece square = null;
        for (int i = 0; i < pieces.length; i++) {
            newPieces[i] = (Piece) pieces[i].clone();
            if (newPieces[i].shape.equals(Shape.kSquare)) {
                square = newPieces[i];
            }
        }
        assert square != null;
        State state = new State(newPieces, square);
        state.step = step;
        return state;
    }

    public Mask createMask() {
        if (mask != null)
            return mask;
        mask = new Mask(width, height);
        for (int i = 0; i < pieces.length; i++) {
            Piece piece = pieces[i];
            mask.fillSpace(piece.top, piece.left, piece.right(), piece.bottom(), piece.getShapeIndex());
        }
        return mask;
    }

    boolean isSolved() {
        assert square.shape == Shape.kSquare;
        return (square.left == 1 && square.top == 3);
    }

    void doMove(Move func) {
        Mask mask = createMask();

        for (int i = 0; i < pieces.length; i++) {
            Piece p = pieces[i];

            if (p.top > 0 && mask.isEmpty(p.left, p.top-1) && mask.isEmpty(p.right(), p.top-1)) {
                // MOVE UP
                State next = (State) this.clone();
                next.step++;
                next.pieces[i].top--;
                next.createMask();
                next.mask.howToMoveToThis = new Movement(new Coordinate(p.left, p.top), new Coordinate(p.left, p.top-1), i, Movement.MOVE_UP, 1);
                next.mask.parent = this.mask;
                func.move(next);
            }

            if (p.bottom() < height - 1 && mask.isEmpty(p.left, p.bottom()+1) && mask.isEmpty(p.right(), p.bottom()+1)) {
                // MOVE DOWN
                State next = (State) this.clone();
                next.step++;
                next.pieces[i].top++;
                next.createMask();
                next.mask.howToMoveToThis = new Movement(new Coordinate(p.left, p.top), new Coordinate(p.left, p.top+1), i, Movement.MOVE_DOWN, 1);
                next.mask.parent = this.mask;
                func.move(next);
            }

            if (p.left > 0 && mask.isEmpty(p.left-1, p.top) && mask.isEmpty(p.left-1, p.bottom())) {
                // MOVE LEFT
                State next = (State) this.clone();
                next.step++;
                next.pieces[i].left--;
                next.createMask();
                next.mask.howToMoveToThis = new Movement(new Coordinate(p.left, p.top), new Coordinate(p.left-1, p.top), i, Movement.MOVE_LEFT, 1);
                next.mask.parent = this.mask;
                func.move(next);
            }

            if (p.right() < width - 1 && mask.isEmpty(p.right()+1, p.top) && mask.isEmpty(p.right()+1, p.bottom())) {
                // MOVE RIGHT
                State next = (State) this.clone();
                next.step++;
                next.pieces[i].left++;
                next.createMask();
                next.mask.howToMoveToThis = new Movement(new Coordinate(p.left, p.top), new Coordinate(p.left+1, p.top), i, Movement.MOVE_RIGHT, 1);
                next.mask.parent = this.mask;
                func.move(next);
            }
        }
    }
}
