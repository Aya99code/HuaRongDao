package com.example.huarongdao.model;

import java.util.LinkedList;

import static com.example.huarongdao.MainActivity.height;
import static com.example.huarongdao.MainActivity.width;
import static com.example.huarongdao.model.Movement.MOVE_LEFT;
import static com.example.huarongdao.model.Movement.MOVE_RIGHT;
import static com.example.huarongdao.model.Movement.MOVE_UP;
import static com.example.huarongdao.model.Movement.MOVE_DOWN;


public class Board {
    public Piece[] pieces;
    int step;
    Piece square;

    Mask mask;

    public LinkedList<Movement> answers;


    LinkedList<Movement> movementsHasDone = new LinkedList<>();
    LinkedList<Movement> movementsUndone = new LinkedList<>();

    public Board() {
        this.pieces = new Piece[10];
        pieces[0] = new Piece(Shape.kVertical, 0, 0, "黄忠");
        pieces[1] = new Piece(Shape.kSquare,   1, 0, "曹操");
        pieces[2] = new Piece(Shape.kVertical, 3, 0, "赵云");
        pieces[3] = new Piece(Shape.kVertical, 0, 2, "张飞");
        pieces[4] = new Piece(Shape.kHorizon,  1, 2, "关羽");
        pieces[5] = new Piece(Shape.kVertical, 3, 2, "马超");
        pieces[6] = new Piece(Shape.kSingle, 1, 3, "兵");
        pieces[7] = new Piece(Shape.kSingle, 2, 3, "兵");
        pieces[8] = new Piece(Shape.kSingle, 0, 4, "兵");
        pieces[9] = new Piece(Shape.kSingle, 3, 4, "兵");
        square = pieces[1];
        createMask();
        answers = new LinkedList<>();
//        mask.outputBoard();
    }

    public Board(int boardIndex) {
        this.pieces = new Piece[10];
        switch (boardIndex) {
            case 0: // 横刀立马
                pieces[0] = new Piece(Shape.kVertical, 0, 0, "黄忠");
                pieces[1] = new Piece(Shape.kSquare,   1, 0, "曹操");
                pieces[2] = new Piece(Shape.kVertical, 3, 0, "赵云");
                pieces[3] = new Piece(Shape.kVertical, 0, 2, "张飞");
                pieces[4] = new Piece(Shape.kHorizon,  1, 2, "关羽");
                pieces[5] = new Piece(Shape.kVertical, 3, 2, "马超");
                pieces[6] = new Piece(Shape.kSingle, 1, 3, "兵");
                pieces[7] = new Piece(Shape.kSingle, 2, 3, "兵");
                pieces[8] = new Piece(Shape.kSingle, 0, 4, "兵");
                pieces[9] = new Piece(Shape.kSingle, 3, 4, "兵");
                square = pieces[1];
                break;
            case 1: // 齐头并进
                pieces[0] = new Piece(Shape.kVertical, 0, 0, "黄忠");
                pieces[1] = new Piece(Shape.kSquare,   1, 0, "曹操");
                pieces[2] = new Piece(Shape.kVertical, 3, 0, "赵云");
                pieces[3] = new Piece(Shape.kVertical, 0, 3, "张飞");
                pieces[4] = new Piece(Shape.kHorizon,  1, 3, "关羽");
                pieces[5] = new Piece(Shape.kVertical, 3, 3, "马超");
                pieces[6] = new Piece(Shape.kSingle, 0, 2, "兵");
                pieces[7] = new Piece(Shape.kSingle, 1, 2, "兵");
                pieces[8] = new Piece(Shape.kSingle, 2, 2, "兵");
                pieces[9] = new Piece(Shape.kSingle, 3, 2, "兵");
                square = pieces[1];
                break;
            case 2: // 兵分三路
                pieces[0] = new Piece(Shape.kVertical, 0, 1, "黄忠");
                pieces[1] = new Piece(Shape.kSquare,   1, 0, "曹操");
                pieces[2] = new Piece(Shape.kVertical, 3, 1, "赵云");
                pieces[3] = new Piece(Shape.kVertical, 0, 3, "张飞");
                pieces[4] = new Piece(Shape.kHorizon,  1, 2, "关羽");
                pieces[5] = new Piece(Shape.kVertical, 3, 3, "马超");
                pieces[6] = new Piece(Shape.kSingle, 1, 3, "兵");
                pieces[7] = new Piece(Shape.kSingle, 2, 3, "兵");
                pieces[8] = new Piece(Shape.kSingle, 0, 0, "兵");
                pieces[9] = new Piece(Shape.kSingle, 3, 0, "兵");
                square = pieces[1];
                break;
            case 3: // 屯兵东路
                pieces[0] = new Piece(Shape.kVertical, 2, 0, "黄忠");
                pieces[1] = new Piece(Shape.kSquare,   0, 0, "曹操");
                pieces[2] = new Piece(Shape.kVertical, 3, 0, "赵云");
                pieces[3] = new Piece(Shape.kVertical, 0, 3, "张飞");
                pieces[4] = new Piece(Shape.kHorizon,  0, 2, "关羽");
                pieces[5] = new Piece(Shape.kVertical, 1, 3, "马超");
                pieces[6] = new Piece(Shape.kSingle, 2, 2, "兵");
                pieces[7] = new Piece(Shape.kSingle, 3, 2, "兵");
                pieces[8] = new Piece(Shape.kSingle, 2, 3, "兵");
                pieces[9] = new Piece(Shape.kSingle, 3, 3, "兵");
                square = pieces[1];
        }
        createMask();
        answers = new LinkedList<>();
    }

    public void createMask() {
        mask = new Mask(width, height);
        for (int i = 0; i < pieces.length; i++) {
            Piece piece = pieces[i];
            mask.fillSpace(piece.top, piece.left, piece.right(), piece.bottom(), Piece.createTypeDataOfPiece(piece, i));
        }
    }

    public Board(Piece[] pieces, Piece square) {
        this.pieces = pieces;
        this.square = square;
    }

    public int getStep() {
        return step;
    }

    public Mask solveBoard() {
        Solution solution = new Solution(this);
        solution.calculateSolution();
        if (solution.toMuchSteps)
            return null;
        return solution.endGame;
    }

    public boolean isSolved() {
        assert square.shape == Shape.kSquare;
        return (square.left == 1 && square.top == 3);
    }

    public boolean checkMoveable(Movement movement) {
//        mask.outputBoard();
        Coordinate to;
        Coordinate from = movement.from;
        final int direction = movement.direction;
        final int index = movement.pieceIndex;
        final int step = movement.step;
        if (direction == -1)
            return false;
        Coordinate temp = new Coordinate(0, 0);
        int st;
        for (st = 1; st <= step; st++) {
            temp = transDirectionToNewPosition(direction, temp, st);
            if (!isNotOverlapping(temp.x, temp.y, index))
                return false;
        }
        return true;
    }

    private Coordinate transDirectionToNewPosition(final int direction, final Coordinate position, int step) {
        Coordinate newPos = position;
        switch (direction) {
            case MOVE_UP:
                newPos = position.set(0, -step);
                break;
            case MOVE_DOWN:
                newPos = position.set(0, step);
                break;
            case MOVE_LEFT:
                newPos = position.set(-step, 0);
                break;
            case MOVE_RIGHT:
                newPos = position.set(step, 0);
                break;
        }
        return newPos;
    }

    public void moveSolution(Movement movement) {
        Coordinate to = movement.to;
        final int index = movement.pieceIndex;
        Piece piece = pieces[index];
        mask.clearSpace(piece.top, piece.left, piece.right(), piece.bottom());
        piece.top = to.y;
        piece.left = to.x;
        mask.fillSpace(piece.top, piece.left, piece.right(), piece.bottom(), Piece.createTypeDataOfPiece(piece, index));
        this.step++;
        movementsHasDone.addFirst(movement);
        movementsUndone.clear();
    }

    public Coordinate move(Movement movement) {
//        mask.outputBoard();
        final Coordinate from = movement.from;
        Coordinate to = movement.to;
        final int direction = movement.direction;
        int x = to.x - from.x; // delta x
        int y = to.y - from.y; // delta y
        if (direction == -1 || (x == 0 && y == 0))
            return (Coordinate) from.clone();
        final int index = movement.pieceIndex;
        int step = movement.step;
        Coordinate temp = new Coordinate(0, 0);
        to = (Coordinate) from.clone();
        int st;
        for (st = 1; st <= step; st++) {
            temp = transDirectionToNewPosition(direction, temp, st);
            if (!isNotOverlapping(temp.x, temp.y, index))
                break;
            to = to.set(from.x + temp.x, from.y + temp.y);
        }
        if (to.equals(from))
            return (Coordinate) from.clone();
        Piece piece = pieces[index];
        mask.clearSpace(piece.top, piece.left, piece.right(), piece.bottom());
        piece.top = to.y;
        piece.left = to.x;
        mask.fillSpace(piece.top, piece.left, piece.right(), piece.bottom(), Piece.createTypeDataOfPiece(piece, index));
        this.step++;
        movement.to = to;
        movement.step = st;
        movementsHasDone.addFirst(movement);
        movementsUndone.clear();
        if (!answers.isEmpty() && movement.equals(answers.getFirst())) {
            answers.pop();
        } else if (!answers.isEmpty()) {
            answers = new LinkedList<>();
        }
//        mask.outputBoard();
        return to;
    }

    private boolean isNotOverlapping(int x, int y, int pieceIndex) { // x, y is delta coordinate
        Piece piece = pieces[pieceIndex];
        if (isOutOfBoard(x, y, pieceIndex))
            return false;
        return mask.canMove(piece.top+y, piece.left+x, piece.right()+x, piece.bottom()+y, Piece.createTypeDataOfPiece(piece, pieceIndex));
    }

    private boolean isOutOfBoard(int x, int y, int pieceIndex) { // x, y is delta coordinate
        Piece piece = pieces[pieceIndex];
        return (isOutOfBoard(piece.left+x, piece.top+y) || isOutOfBoard(piece.right()+x, piece.bottom()+y));
    }

    private boolean isOutOfBoard(int x, int y) { // x, y is real coordinate
        return x < 0 || y < 0 || x >= width || y >= height;
    }

    public Movement undo() {
        if (movementsHasDone.size() < 1)
            return null;
        Movement movement = movementsHasDone.getFirst();
        movementsHasDone.pop();
        movement.invert();
        movementsUndone.addFirst(movement);
        this.step--;
        this.answers = new LinkedList<>();
        Piece piece = pieces[movement.pieceIndex];
        mask.clearSpace(piece.top, piece.left, piece.right(), piece.bottom());
        piece.top = movement.to.y;
        piece.left = movement.to.x;
        mask.fillSpace(piece.top, piece.left, piece.right(), piece.bottom(), Piece.createTypeDataOfPiece(piece, movement.pieceIndex));
        return movement;
    }

    public Movement redo() {
        if (movementsUndone.size() < 1)
            return null;
        Movement movement = movementsUndone.getFirst();
        movementsUndone.pop();
        movement.invert();
        movementsHasDone.addFirst(movement);
        this.step++;
        this.answers = new LinkedList<>();
        Piece piece = pieces[movement.pieceIndex];
        mask.clearSpace(piece.top, piece.left, piece.right(), piece.bottom());
        piece.top = movement.to.y;
        piece.left = movement.to.x;
        mask.fillSpace(piece.top, piece.left, piece.right(), piece.bottom(), Piece.createTypeDataOfPiece(piece, movement.pieceIndex));
        return movement;
    }
}
