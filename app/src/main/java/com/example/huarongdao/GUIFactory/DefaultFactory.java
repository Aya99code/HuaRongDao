package com.example.huarongdao.GUIFactory;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;

import com.example.huarongdao.model.Piece;

public class DefaultFactory extends GUIFactory {

    @Override
    public View createPieceView(Context context, Piece piece) {
        Button button = new Button(context);
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        button.setText(piece.name);
        button.setId(View.generateViewId());
        return button;
    }

    @Override
    public View createButton(Context context, String text) {
        Button button = new Button(context);
        button.setId(View.generateViewId());
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        button.setText(text);
        return button;
    }
}
