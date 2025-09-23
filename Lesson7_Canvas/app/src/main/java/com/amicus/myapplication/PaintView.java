package com.amicus.myapplication;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;

public class PaintView extends View {
    ArrayList<Stroke> strokes = new ArrayList<>();
    Stroke currentStroke;
    int currentColor = Color.BLACK;
    float strokeWidth = 10f;

    private float[] thicknessOptions = {5f, 10f, 15f, 20f, 25f};
    private int currentThicknessIndex = 1;

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        for (Stroke stroke:strokes){
            canvas.drawPath(stroke.path,stroke.paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
               currentStroke = new Stroke(currentColor,strokeWidth);
               currentStroke.path.moveTo(x,y);
               strokes.add(currentStroke);
               return true;
            case MotionEvent.ACTION_MOVE:
                if (currentStroke != null) {
                    currentStroke.path.lineTo(x,y);
                }
                break;
            case  MotionEvent.ACTION_UP:
                break;
        }
        invalidate();
        return true;
    }
    public void clearCanvas(){
        strokes.clear();
        invalidate();
    }

    public void changeColor(Button btn){
        Random random = new Random();
        currentColor = Color.rgb(random.nextInt(256),random.nextInt(256),random.nextInt(256));

        strokeWidth = thicknessOptions[currentThicknessIndex];
        if (changeThicknessButton != null) {
            changeThicknessButton.setText("Size: " + (int)strokeWidth + "px");
        }

        btn.setBackgroundColor(currentColor);
    }

    public void saveToGallery(Context context){
        Bitmap bitmap = Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        draw(canvas);
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME,"drawing_"+System.currentTimeMillis()+".png");
        values.put(MediaStore.Images.Media.MIME_TYPE,"image/png");
        values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES+"/MyDrawings");

        ContentResolver resolver = context.getContentResolver();

        try {
            Uri uri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
            Uri imageUri = resolver.insert(uri,values);
            if (imageUri != null) {
                try (OutputStream outputStream = resolver.openOutputStream(imageUri)){
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
                }
            }
            Toast.makeText(context, "Сохранено в галерее", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, "Ошибка сохранения", Toast.LENGTH_SHORT).show();
        }
    }

    public  void  undoStroke()
    {
        if (strokes.toArray().length > 0)
        {
            strokes.remove(strokes.toArray().length-1);
            invalidate();
        }
    }


    public void changeThickness() {
        currentThicknessIndex = (currentThicknessIndex + 1) % thicknessOptions.length;
        strokeWidth = thicknessOptions[currentThicknessIndex];

        if (changeThicknessButton != null) {
            changeThicknessButton.setText("Size: " + (int)strokeWidth + "px");
        }
    }

    public  void  erase()
    {
        currentColor = Color.WHITE;
        strokeWidth = 75f;
        invalidate();
    }


    public void setStrokeWidth(float width) {
        strokeWidth = width;
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    private Button changeThicknessButton;

    public void setChangeThicknessButton(Button button) {
        this.changeThicknessButton = button;
        updateButtonText();
    }

    private void updateButtonText() {
        if (changeThicknessButton != null) {
            changeThicknessButton.setText("Size: " + (int)strokeWidth + "px");
        }
    }
}
