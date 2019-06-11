package com.example.huarongdao.GUIFactory;

import android.content.Context;
import android.view.View;

import com.example.huarongdao.model.Piece;

public abstract class GUIFactory {

    abstract public View createPieceView(Context context, Piece piece);
    abstract public View createButton(Context context, String text);

}
