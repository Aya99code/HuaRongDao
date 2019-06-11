package com.example.huarongdao;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.huarongdao.model.Board;
import com.example.huarongdao.model.Coordinate;
import com.example.huarongdao.model.Movement;
import com.example.huarongdao.model.Piece;

public class PieceOnTouchListener implements View.OnTouchListener {

    private final int blockSize;
    private final int horizontalMargin;
    private final int index;
    private final Board board;
    private final MainActivity mainActivity;
    private final Piece piece;

    private float deltaX, deltaY;
    private float originalRawX, originalRawY;
    private int originalParamLeft, originalParamTop;

    private Coordinate position;
    private Coordinate to;

    public PieceOnTouchListener(MainActivity mainActivity, int index) {
        this.blockSize = mainActivity.blockSize;
        this.horizontalMargin = mainActivity.margin;
        this.index = index;
        this.piece = mainActivity.board.pieces[index];
        this.position = new Coordinate(piece.left(), piece.top());
        this.board = mainActivity.board;
        this.mainActivity = mainActivity;
        this.to = (Coordinate) position.clone();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        RelativeLayout.LayoutParams params;
        int step = 0;
        int direction = -1;
        this.position = this.position.set(this.piece.left(), this.piece.top());
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                setOriginalRawPosition(event.getRawX(), event.getRawY());
                setOriginalParam(v);
                break;
            case MotionEvent.ACTION_MOVE:
                deltaX = event.getRawX() - originalRawX;
                deltaY = event.getRawY() - originalRawY;
                step = (int)(Math.abs(Math.abs(deltaX) > Math.abs(deltaY) ? deltaX : deltaY) / blockSize + 1);
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    if (deltaX > 0) {
                        direction = Movement.MOVE_RIGHT;
                        to = to.set(position.x + step, position.y);
                    } else if (deltaX < 0) {
                        direction = Movement.MOVE_LEFT;
                        to = to.set(position.x - step, position.y);
                    } else  {
                        to = to.set(position.x, position.y);
                    }
                    if (board.checkMoveable(new Movement(position, to, index, direction, step))) {
                        params = (RelativeLayout.LayoutParams) v.getLayoutParams();
                        params.leftMargin = originalParamLeft + (int)deltaX;
                        v.setLayoutParams(params);
                    }
                } else {
                    if (deltaY > 0) {
                        direction = Movement.MOVE_DOWN;
                        to = to.set(position.x, position.y + step);
                    } else if (deltaY < 0) {
                        direction = Movement.MOVE_UP;
                        to = to.set(position.x, position.y - step);
                    } else {
                        to = to.set(position.x, position.y);
                    }
                    if (board.checkMoveable(new Movement(position, to, index, direction, step))) {
                        params = (RelativeLayout.LayoutParams) v.getLayoutParams();
                        params.topMargin = originalParamTop + (int)deltaY;
                        v.setLayoutParams(params);
                    }

                }
                break;
            case MotionEvent.ACTION_UP:
                deltaX = event.getRawX() - originalRawX;
                deltaY = event.getRawY() - originalRawY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    step = (int)(Math.abs(deltaX) / blockSize + 1);
                    if (deltaX > 0) {
                        direction = Movement.MOVE_RIGHT;
                        to = to.set(position.x + step, position.y);
                    } else if (deltaX < 0) {
                        direction = Movement.MOVE_LEFT;
                        to = to.set(position.x - step, position.y);
                    } else  {
                        direction = -1;
                        to = to.set(position.x, position.y);
                    }
                } else {
                    step = (int)(Math.abs(deltaY) / blockSize + 1);
                    if (deltaY > 0) {
                        direction = Movement.MOVE_DOWN;
                        to = to.set(position.x, position.y + step);
                    } else if (deltaY < 0) {
                        direction = Movement.MOVE_UP;
                        to = to.set(position.x, position.y - step);
                    } else {
                        direction = -1;
                        to = to.set(position.x, position.y);
                    }
                }
                Coordinate newPos = board.move(new Movement(position, to, index, direction, step));
                params = (RelativeLayout.LayoutParams) v.getLayoutParams();
                if (!newPos.equals(position)) {
                    params.leftMargin = newPos.x * blockSize + horizontalMargin / 2;
                    params.topMargin = newPos.y * blockSize + horizontalMargin / 2;
                    v.setLayoutParams(params);
                    mainActivity.flushStep();
                    position = (Coordinate) newPos.clone();
                    if (mainActivity.board.isSolved()) {
                        mainActivity.alertWin();
                    }
                } else {
                    params.leftMargin = originalParamLeft;
                    params.topMargin = originalParamTop;
                    v.setLayoutParams(params);
                }
                break;
        }
        return true;
    }

    private void setOriginalParam(View v) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
        this.originalParamLeft = params.leftMargin;
        this.originalParamTop = params.topMargin;
    }

    private void setOriginalRawPosition(float x, float y) {
        this.originalRawX = x;
        this.originalRawY = y;
    }
}
