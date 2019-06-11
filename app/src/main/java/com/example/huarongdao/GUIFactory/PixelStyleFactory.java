package com.example.huarongdao.GUIFactory;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.huarongdao.R;
import com.example.huarongdao.model.Piece;

public class PixelStyleFactory extends GUIFactory {
    @Override
    public ImageView createPieceView(Context context, Piece piece) {
        ImageView imageView = new ImageView(context);
        imageView.setId(View.generateViewId());
        String name = piece.name;
        switch (name) {
            case "关羽":
                imageView.setImageResource(R.drawable.guanyu);
                break;
            case "曹操":
                imageView.setImageResource(R.drawable.caocao);
                break;
            case "兵":
                imageView.setImageResource(R.drawable.bing);
                break;
            case "张飞":
                imageView.setImageResource(R.drawable.zhangfei);
                break;
            case "赵云":
                imageView.setImageResource(R.drawable.zhaoyun);
                break;
            case "黄忠":
                imageView.setImageResource(R.drawable.huangzhong);
                break;
            case "马超":
                imageView.setImageResource(R.drawable.machao);
                break;
        }
        return imageView;
    }

    @Override
    public View createButton(Context context, String text) {
//        ImageView imageView = new ImageView(context);
//        imageView.setId(View.generateViewId());
//        switch (text) {
//            case "flush":
//                break;
//            case "redo":
//                break;
//            case "undo":
//                break;
//            case "solve":
//                break;
//            case "改变风格":
//                break;
//        }
//        return imageView;
        Button button = new Button(context);
        button.setId(View.generateViewId());
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        button.setText(text);
        return button;
    }


}
