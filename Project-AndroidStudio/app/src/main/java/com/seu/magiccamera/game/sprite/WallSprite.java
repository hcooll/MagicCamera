package com.seu.magiccamera.game.sprite;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by hc on 2017/4/11 0011.
 * 障碍：墙壁
 */

public class WallSprite extends BaseSprite {

    public ArrayList<int[]> walls = new ArrayList<int[]>();
    private ArrayList<int[]> remove_walls = new ArrayList<int[]>();

    private float wall_step;  // 墙与墙之间的距离
    private int move_step; // 移动的距离

    public float speed;  // 屏幕向左移动的速度

    private Paint mSysPaint;

    public WallSprite(float screenWidth, float screenHigh) {
        super(screenWidth, screenHigh, screenWidth / 8, screenHigh / 5);

        wall_step = width * 4;

        speed = (int) (screenWidth / 100f);

        mSysPaint = new Paint();
        mSysPaint.setColor(Color.WHITE);
        mSysPaint.setAntiAlias(true);
        mSysPaint.setStyle(Paint.Style.STROKE); //空心
    }

    public void init() {

        walls.clear();

    }

    public void move(int floorTop) {
        // 向左移动
        remove_walls.clear();
        for (int i = 0; i < walls.size(); i++) {
            int[] wall = walls.get(i);
            wall[0] -= speed;
            if (wall[0] < -width) {
                remove_walls.add(wall);
            }
        }


        // 移除不用的墙壁
        if (remove_walls.size() > 0) {
            walls.removeAll(remove_walls);
        }

        // 增加新的墙壁
        move_step += speed;
        if (move_step > wall_step) {
            int[] wall = new int[]{(int) screenWidth, (int) (Math.random() * (floorTop - 2 * high) + 0.5 * high)};
            walls.add(wall);
            move_step = 0;
        }
    }

    public void draw(Canvas canvas, int floorTop) {
        for (int i = 0; i < walls.size(); i++) {
            int[] wall = walls.get(i);

            float[] pts = {
                    // 墙的上部分
                    wall[0], 0, wall[0], wall[1],
                    wall[0], wall[1], wall[0] + width, wall[1],
                    wall[0] + width, 0, wall[0] + width, wall[1],

                    // 墙的下部分
                    wall[0], wall[1] + high, wall[0], floorTop,
                    wall[0], wall[1] + high, wall[0] + width, wall[1] + high,
                    wall[0] + width, wall[1] + high, wall[0] + width, floorTop
                    //,wall[0],floor[1], wall[0]+wall_w, floor[1]
            };
            canvas.drawLines(pts, mSysPaint);

            //canvas.drawRect(wall[0], 0, wall[0]+wall_w, wall[1], mSysPaint);
            //canvas.drawRect(wall[0], wall[1]+wall_h, wall[0]+wall_w, floor[1], mSysPaint);
        }
    }

}
