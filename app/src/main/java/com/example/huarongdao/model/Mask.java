package com.example.huarongdao.model;

import android.util.Log;

import java.util.Arrays;

public class Mask {
    private short[][] board;

    private int hash;

    public Mask parent = null;
    public Movement howToMoveToThis = null;

    public Mask(int width, int height) {
        board = new short[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = 0;
            }
        }
        hashCode();
    }

    @Override
    public int hashCode() {
        int ret = 0;
        for (int i = 0; i < board.length; i++) {
            ret = 7*ret + Arrays.hashCode(board[i]);
        }
        hash = ret;
        return ret;
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj){
            return true;
        }
        if(!(obj instanceof Mask)){
            return false;
        }
        return hash == ((Mask)obj).hash;
    }

    public boolean isEmpty(int x, int y) {
        return board[y][x] == 0;
    }

    public boolean canMove(int top, int left, int right, int bottom, int value) {
        if (top == bottom && left == right) {
            return board[top][left] == 0 || board[top][left] == value;
        } else if (top == bottom) {
            return (board[top][left] == 0 || board[top][left] == value) &&
                    (board[top][right] == 0 || board[top][right] == value);
        } else if (left == right) {
            return (board[top][left] == 0 || board[top][left] == value) &&
                    (board[bottom][left] == 0 || board[bottom][left] == value);
        } else {
            return
                (board[top][left] == 0 || board[top][left] == value) &&
                (board[top][right] == 0 || board[top][right] == value) &&
                (board[bottom][left] == 0 || board[bottom][left] == value) &&
                (board[bottom][right] == 0 || board[bottom][right] == value);
        }
    }

    public void clearSpace(int top, int left, int right, int bottom) {
        if (top == bottom && left == right) {
            board[top][left] = 0;
        } else if (top == bottom) {
            board[top][left] = 0;
            board[top][right] = 0;
        } else if (left == right) {
            board[top][left] = 0;
            board[bottom][left] = 0;
        } else {
            board[top][left] = 0;
            board[top][right] = 0;
            board[bottom][left] = 0;
            board[bottom][right] = 0;
        }
    }

    public void fillSpace(int top, int left, int right, int bottom, int value) {
        if (top == bottom && left == right) {
            board[top][left] = (short) value;
        } else if (top == bottom) {
            board[top][left] = (short) value;
            board[top][right] = (short) value;
        } else if (left == right) {
            board[top][left] = (short) value;
            board[bottom][left] = (short) value;
        } else {
            board[top][left] = (short) value;
            board[top][right] = (short) value;
            board[bottom][left] = (short) value;
            board[bottom][right] = (short) value;
        }
    }

    public void outputBoard() {
        Log.d("\n", "\n");
        Log.d("board[0]", "\t" + Integer.toHexString(board[0][0]) + "\t" + Integer.toHexString(board[0][1])+"\t"+Integer.toHexString(board[0][2])+"\t"+Integer.toHexString(board[0][3]));
        Log.d("board[1]", "\t" + Integer.toHexString(board[1][0]) + "\t" + Integer.toHexString(board[1][1])+"\t"+Integer.toHexString(board[1][2])+"\t"+Integer.toHexString(board[1][3]));
        Log.d("board[2]", "\t" + Integer.toHexString(board[2][0]) + "\t" + Integer.toHexString(board[2][1])+"\t"+Integer.toHexString(board[2][2])+"\t"+Integer.toHexString(board[2][3]));
        Log.d("board[3]", "\t" + Integer.toHexString(board[3][0]) + "\t" + Integer.toHexString(board[3][1])+"\t"+Integer.toHexString(board[3][2])+"\t"+Integer.toHexString(board[3][3]));
        Log.d("board[4]", "\t" + Integer.toHexString(board[4][0]) + "\t" + Integer.toHexString(board[4][1])+"\t"+Integer.toHexString(board[4][2])+"\t"+Integer.toHexString(board[4][3]));
        Log.d("\n", "\n");
    }
}
