package com.example.huarongdao;

import android.app.AlertDialog;
import android.graphics.Point;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.huarongdao.GUIFactory.DefaultFactory;
import com.example.huarongdao.GUIFactory.GUIFactory;
import com.example.huarongdao.GUIFactory.PictureStyleFactory;
import com.example.huarongdao.GUIFactory.PixelStyleFactory;
import com.example.huarongdao.model.Board;
import com.example.huarongdao.model.Coordinate;
import com.example.huarongdao.model.Mask;
import com.example.huarongdao.model.Movement;
import com.example.huarongdao.model.Piece;

import java.util.LinkedList;

import static java.lang.Thread.sleep;


public class MainActivity extends AppCompatActivity {

    View[] pieceViews;
    Board board;
    private GUIFactory guiFactory;

    int blockSize;
    int margin = 40;
    public static int width = 4, height = 5;

    RelativeLayout boardLayout;

    LinkedList<Movement> answers;
    LinearLayout page;
    MainActivity that = this;

    private GUIFactory[] guiFactorys;
    private int styles = 3; // number of gui factory
    private int guiFactoryIndex = 0;

    private int boardIndex = 0;
    private int totalBoardIndex = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boardLayout = findViewById(R.id.board_page);
        page = findViewById(R.id.page);
        initGUIFactory();
        initOpetationsButtons();
        initGame();
    }

    private void initGame() {
        initBlockSize();
        board = new Board(boardIndex);
        initGameBoard();
        answers = new LinkedList<>();
        flushStep();
    }

    public void initGameBoard() {
        pieceViews = new View[board.pieces.length];
        for (int i = 0; i < pieceViews.length; i++) {
            Piece piece = board.pieces[i];
            pieceViews[i] = guiFactory.createPieceView(this, piece);
            pieceViews[i].setOnTouchListener(new PieceOnTouchListener(this, i));
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((piece.right()-piece.left() + 1)*blockSize, (piece.bottom()-piece.top() + 1)*blockSize);
            params.leftMargin = piece.left() * blockSize + margin / 2;
            params.topMargin = piece.top() * blockSize + margin / 2;
            boardLayout.addView(pieceViews[i], params);
        }
    }

    public void initOpetationsButtons() {
        View view;

        LinearLayout edit = findViewById(R.id.edit_buttons);
        edit.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = (float) 1;

        view = guiFactory.createButton(this, "undo");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                that.undo(v);
            }
        });
        edit.addView(view, params);

        view = guiFactory.createButton(this, "redo");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                that.redo(v);
            }
        });
        edit.addView(view, params);

        LinearLayout gameButtons = findViewById(R.id.game_buttons);
        gameButtons.removeAllViews();
        view = guiFactory.createButton(this, "flush");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                that.reopen(v);
            }
        });
        gameButtons.addView(view, params);

        view = guiFactory.createButton(this, "solve");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                that.solve(v);
            }
        });
        gameButtons.addView(view, params);

        LinearLayout changeStyle = findViewById(R.id.change_style);
        changeStyle.removeAllViews();
        view = guiFactory.createButton(this, "更换风格");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                that.changeStyle();
            }
        });
//        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        changeStyle.addView(view, params);

        view = guiFactory.createButton(this, "更换关卡");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                that.changeBoardIndex();
            }
        });
        changeStyle.addView(view, params);

    }

    private void initBlockSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int min = size.x < size.y ? size.x : size.y;
        blockSize = (min - margin) / 4;
    }

    public void flushStep() {
        TextView view = findViewById(R.id.step); // TODO modify step view
        String step = getString(R.string.step);
        view.setText(step + String.valueOf(board.getStep()));
    }

    public void reopen(View view) {
        boardLayout.removeAllViews();
        initGame();
        flushStep();
    }

    public void undo(View view) {
        Movement movement = board.undo();
        if (movement == null) {
            return;
        }
        View v = pieceViews[movement.pieceIndex];
        Coordinate newPos = movement.to;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
        params.leftMargin = newPos.x * blockSize + margin / 2;
        params.topMargin = newPos.y * blockSize + margin / 2;
        v.setLayoutParams(params);
        flushStep();
    }

    public void redo(View view) {
        Movement movement = board.redo();
        if (movement == null) {
            return;
        }
        View v = pieceViews[movement.pieceIndex];
        Coordinate newPos = movement.to;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
        params.leftMargin = newPos.x * blockSize + margin / 2;
        params.topMargin = newPos.y * blockSize + margin / 2;
        v.setLayoutParams(params);
        flushStep();
    }

    public void initGUIFactory() {
        guiFactorys = new GUIFactory[styles];

        guiFactorys[0] = new DefaultFactory();
        guiFactorys[1] = new PixelStyleFactory();
        guiFactorys[2] = new PictureStyleFactory();

        guiFactory = guiFactorys[0];
    }

    public void changeBoardIndex() {
        boardLayout.removeAllViews();
        boardIndex = boardIndex == totalBoardIndex - 1 ? 0 : boardIndex + 1;
        initGame();
    }

    public void changeStyle() {
        guiFactoryIndex++;
        if (guiFactoryIndex == styles) guiFactoryIndex = 0;
        guiFactory = guiFactorys[guiFactoryIndex];
        boardLayout.removeAllViews();
        LinearLayout gameButtons = findViewById(R.id.game_buttons);
        gameButtons.removeAllViews();
        LinearLayout edit = findViewById(R.id.edit_buttons);
        edit.removeAllViews();
        LinearLayout changeStyle = findViewById(R.id.change_style);
        changeStyle.removeAllViews();
        initGameBoard();
        initOpetationsButtons();
    }

    public void solve(View view) {
        if (board.answers.isEmpty() && !board.isSolved()) {
            Mask mask = board.solveBoard();
            if (mask == null) {
                // TODO alert suan bu chu
                return;
            }
            while (mask.parent!=null) {
                board.answers.addFirst(mask.howToMoveToThis);
                mask = mask.parent;
            }
        }
        if (board.isSolved()) {
            alertWin();
            return;
        }
        answers = board.answers;
        Movement movement = answers.pop();
        final View v = pieceViews[movement.pieceIndex];
        board.moveSolution(movement);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
        params.leftMargin = movement.to.x * blockSize + margin / 2;
        params.topMargin = movement.to.y * blockSize + margin / 2;
        v.setLayoutParams(params);
        flushStep();
    }

    public void alertWin() {

    }

    class SolutionAsyncTask extends AsyncTask<Void, Void, Mask>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("suansuansuan");
            builder.setMessage("suansuansuan");
            that.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//            progressBar.setVisibility(View.VISIBLE);
//            progressBar.setProgress(0);
        }

        @Override
        protected Mask doInBackground(Void... voids) {
            return board.solveBoard();
        }

        protected void onPostExecute(Mask mask) {
            while (mask.parent!=null) {
                answers.addLast(mask.howToMoveToThis);
                mask = mask.parent;
            }
            that.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//            progressBar.setVisibility(View.GONE);
            while (!answers.isEmpty()) {
                Movement movement = answers.pop();
                if (movement == null) continue;
                final View v = pieceViews[movement.pieceIndex];
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
                params.leftMargin = movement.to.x * blockSize + margin / 2;
                params.topMargin = movement.to.y * blockSize + margin / 2;
                v.setLayoutParams(params);
            }
        }
    }
}
