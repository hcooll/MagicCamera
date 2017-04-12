package com.seu.magiccamera.game.sprite;

/**
 * Created by hc on 2017/4/11 0011.
 * <p>
 * 精灵：游戏里面所有的东西都可以概括为精灵
 * <p>
 * 基本属性：大小、位置等
 */

public class BaseSprite {

    // 屏幕宽高
    float screenWidth;
    float screenHigh;

    // 大小
    public float width;  // 宽
    public float high;  // 高

    // 位置
    public int left;
    public int top;
    public int right;
    public int bottom;


    public BaseSprite(float screenWidth, float screenHigh, float width, float high) {
        this.screenWidth = screenWidth;
        this.screenHigh = screenHigh;
        this.width = width;
        this.high = high;
    }

}
