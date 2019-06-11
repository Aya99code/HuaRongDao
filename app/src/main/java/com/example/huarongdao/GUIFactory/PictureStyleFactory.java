package com.example.huarongdao.GUIFactory;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.huarongdao.R;
import com.example.huarongdao.model.Piece;

public class PictureStyleFactory extends GUIFactory {
    @Override
    public View createPieceView(Context context, Piece piece) {
        ImageView imageView = new ImageView(context);
        imageView.setId(View.generateViewId());
        String name = piece.name;
        switch (name) {
            case "关羽":
                imageView.setImageResource(R.drawable.role_guanyu);
                break;
            case "曹操":
                imageView.setImageResource(R.drawable.role_caocao);
                break;
            case "兵":
                imageView.setImageResource(R.drawable.role_soldier);
                break;
            case "张飞":
                imageView.setImageResource(R.drawable.role_zhangfei);
                break;
            case "赵云":
                imageView.setImageResource(R.drawable.role_zhaoyun);
                break;
            case "黄忠":
                imageView.setImageResource(R.drawable.role_huangzhong);
                break;
            case "马超":
                imageView.setImageResource(R.drawable.role_machao);
                break;
        }
        return imageView;
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
