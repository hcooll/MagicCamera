package com.seu.magiccamera.game.sprite;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.seu.magiccamera.R;

import java.util.ArrayList;

/**
 * Created by hc on 2017/4/11 0011.
 */

public class KittySprite extends BaseSprite {

    Paint mBirdPaint;

    ArrayList<Bitmap> mKittyBitmap;
    int mBitmapIndex;

    private float bird_v; // 当前的速度
    private float bird_a; // 下降的加速度
    private float bird_vUp; // 每一次点击时重置的速度

    public KittySprite(Context context, float screenWidth, float screenHigh) {
        super(screenWidth, screenHigh, screenWidth / 50f, screenHigh / 50f);

        mKittyBitmap = new ArrayList<>();
        Bitmap kitty1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.kitty1);
        Bitmap kitty2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
        mKittyBitmap.add(kitty1);
        mKittyBitmap.add(kitty2);
        mBitmapIndex = 0;

        mBirdPaint = new Paint();
        mBirdPaint.setAntiAlias(true);

    }

    public void init() {
        bird_v = 0f;
        bird_a = screenWidth / 250f;
        bird_vUp = -screenWidth / 80f;

        left = (int) screenWidth / 3;
        top = (int) (screenHigh / 2);
    }

    // 下跌
    public void fall() {
        bird_v += bird_a;
        top += bird_v;
    }

    // 上升
    public void rise() {
        bird_v = bird_vUp;
    }

    public void draw(Canvas canvas) {
        Rect def = new Rect(left, top, left + (int) width, top + (int) high);
        canvas.drawBitmap(getBitmap(), null, def, mBirdPaint);
    }


    private Bitmap getBitmap() {
        Bitmap bitmap = mKittyBitmap.get(mBitmapIndex);
        mBitmapIndex = mBitmapIndex < mKittyBitmap.size() - 1 ? mBitmapIndex++ : 0;
        return bitmap;
    }

}
