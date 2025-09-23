package com.amicus.myapplication;


import android.graphics.Paint;
import android.graphics.Path;


public class Stroke {
    Path path;
    Paint paint;

    public Stroke(int color,float strokeWidth) {
        path = new Path();
        paint = new Paint();
        paint.setColor(color);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setAntiAlias(true);
    }
}
