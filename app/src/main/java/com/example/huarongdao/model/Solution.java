package com.example.huarongdao.model;

import android.util.Log;

import java.util.HashSet;
import java.util.LinkedList;

public class Solution {

    public Mask endGame;
    HashSet<Mask> seen = new HashSet<>();
    LinkedList<State> queue = new LinkedList<>();
    public LinkedList<Mask> answers = new LinkedList<>();
    public boolean toMuchSteps = false;
    State state;

    Solution(Board board){
        state = new State(board);
    }

    public void calculateSolution() {
        queue.push(state);
        seen.add(state.createMask());
        final int[] num = {0};
        toMuchSteps = false;
        while (!queue.isEmpty()) {

            final State curr = queue.pop();
//            Log.d("Solution", "curr:\n");
//            curr.mask.outputBoard();
            if (curr.isSolved()) {
                endGame = curr.mask;
                break;
            } else if (curr.step > 150) {
                Log.d("Solution", "TO MANY steps");
                toMuchSteps = true;
                break;
            }
            curr.doMove(new Move() {
                @Override
                public void move(State state) {
                    Mask mask = state.createMask();
                    if (!seen.contains(mask)) {
                        queue.addLast(state);
                        seen.add(state.createMask());
//                        Log.d("Solution", " step: " + state.step);
//                        mask.outputBoard();
//                        num[0]++;
//                        Log.d("Solution", " count: " + num[0]);
                    }
                }
            });
        }
    }
}
