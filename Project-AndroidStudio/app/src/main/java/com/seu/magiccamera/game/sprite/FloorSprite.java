package com.seu.magiccamera.game.sprite;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by hc on 2017/4/11 0011.
 * <p>
 * 地面
 */

public class FloorSprite extends BaseSprite {

    Paint mSysPaint;
    int speed;


    public FloorSprite(float screenWidth, float screenHigh) {
        super(screenWidth, screenHigh, screenWidth / 25, 1);

        mSysPaint = new Paint();
        mSysPaint.setColor(Color.WHITE);
        mSysPaint.setAntiAlias(true);
        mSysPaint.setStyle(Paint.Style.STROKE); //空心

        speed = (int) (screenWidth / 100f);

    }

    public void init() {
        left = 0;
        top = (int) (screenWidth - screenWidth / 5);
    }

    public void move() {
        if (left < -width) {
            left += width * 2;
        }
        left -= speed;
    }

    public void draw(Canvas canvas) {
        int floor_start = left;
        while (floor_start < screenWidth) {
            canvas.drawLine(floor_start, top, floor_start + width, top, mSysPaint);
            floor_start += width * 2;
        }
    }
}
