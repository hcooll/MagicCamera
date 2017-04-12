package com.seu.magiccamera.game.sprite;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by hc on 2017/4/11 0011.
 * <p>
 * 分数
 */

public class LevelSprite extends BaseSprite {

    private int level_value = 0;  // 得分

    private Paint mSysPaint;

    public LevelSprite(float screenWidth, float screenHigh) {
        super(screenWidth, screenHigh, 0, 0);

       int TEXT_SIZE = (int) (screenWidth / 10);

        mSysPaint = new Paint();
        mSysPaint.setColor(Color.WHITE);
        mSysPaint.setAntiAlias(true);
        mSysPaint.setTextSize(TEXT_SIZE);
        mSysPaint.setStyle(Paint.Style.STROKE); //空心

    }

    public void init(){
        level_value = 0;

        left = (int) (screenWidth / 2);
        top = (int) (screenHigh / 5);
    }

    // 分数增加
    public void score() {
        level_value++;
    }

    public void draw(Canvas canvas) {
        canvas.drawText(String.valueOf(level_value), left, top, mSysPaint);
    }

}
