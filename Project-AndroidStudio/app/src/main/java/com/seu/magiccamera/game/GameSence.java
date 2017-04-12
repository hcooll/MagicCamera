package com.seu.magiccamera.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.util.TypedValue;

import com.seu.magiccamera.game.sprite.FloorSprite;
import com.seu.magiccamera.game.sprite.KittySprite;
import com.seu.magiccamera.game.sprite.LevelSprite;
import com.seu.magiccamera.game.sprite.WallSprite;

/**
 * Created by hc on 2017/3/23 0023.
 */

public class GameSence implements Runnable {

    Context mContext;

    private Thread th;
    private boolean flag;

    private Canvas canvas;
    private static int screenW = 500, screenH = 500;

    private static final int GAME_MENU = 0;
    private static final int GAMEING = 1;
    private static final int GAME_OVER = -1;

    private int gameState = GAME_MENU;
    private boolean gameStateChanged = false;

    Bitmap mSenceBitmap = null;

    LevelSprite mLevelSprite;
    FloorSprite mFloorSprite;
    WallSprite mWallSprite;
    KittySprite mKittySprite;

    public GameSence(Context context) {
        mContext = context;

        // 创建场景图片，之后游戏中所有的东西都画在这个场景中
        mSenceBitmap = Bitmap.createBitmap(screenW, screenH, Bitmap.Config.ARGB_8888);

        mLevelSprite = new LevelSprite(screenW, screenH);
        mFloorSprite = new FloorSprite(screenW, screenH);
        mWallSprite = new WallSprite(screenW, screenH);
        mKittySprite = new KittySprite(context, screenW, screenH);


        canvas = new Canvas(mSenceBitmap);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));

        initGame();
    }

    private void initGame() {

        if (gameState == GAME_MENU) {

            mLevelSprite.init();

            mFloorSprite.init();

            mWallSprite.init();

            mKittySprite.init();

        }
    }

    private float dp2px(float dp) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mContext.getResources().getDisplayMetrics());
        return px;
    }

    public void startFly() {
        flag = true;

        th = new Thread(this);
        th.start();
    }

    public void stopFly() {
        flag = false;
    }


    @Override
    public void run() {
        while (flag) {
            long start = System.currentTimeMillis();
            //myDraw();
            logic();
            long end = System.currentTimeMillis();
            try {
                if (end - start < 50) {
                    Thread.sleep(50 - (end - start));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public Bitmap myDraw() {

        if (!gameStateChanged
                && (gameState == GAME_OVER
                || (gameState == GAME_MENU && mSenceBitmap != null))) {
            gameStateChanged = false;
            return mSenceBitmap;
        }


        try {

            if (canvas != null) {
                //clear
                canvas.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR);
                //mSysPaint.setAlpha(0);
                //canvas.drawRect(60, 60, screenW, screenH, mSysPaint);// 正方形
                //background
                //mSysPaint.setAlpha(255);
                mFloorSprite.draw(canvas);

                //wall
                mWallSprite.draw(canvas, mFloorSprite.top);

                //bird
                //canvas.drawCircle(bird[0], bird[1], bird_width, mSysPaint);
                mKittySprite.draw(canvas);

                //level
                mLevelSprite.draw(canvas);

                canvas.save(Canvas.ALL_SAVE_FLAG);//保存
                //store
                canvas.restore();//存储

            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
        }
        return mSenceBitmap;
    }


    private void logic() {

        switch (gameState) {
            case GAME_MENU:

                break;
            case GAMEING:

                //bird
                mKittySprite.fall();

                // 地面碰撞检测
                if (mKittySprite.top > mFloorSprite.top - mKittySprite.high) {
                    mKittySprite.top = mFloorSprite.top - (int) mKittySprite.high;
                    gameStateChanged = true;
                    gameState = GAME_OVER;
                }

                //floor
                mFloorSprite.move();

                //wall
                mWallSprite.move(mFloorSprite.top);

                // 墙的碰撞检测
                for (int i = 0; i < mWallSprite.walls.size(); i++) {
                    int[] wall = mWallSprite.walls.get(i);
                    if (wall[0] - mKittySprite.width <= mKittySprite.left && wall[0] + mWallSprite.width + mKittySprite.width >= mKittySprite.left
                            && (mKittySprite.top <= wall[1] + mKittySprite.high || mKittySprite.top >= wall[1] + mWallSprite.high - mKittySprite.high)) {
                        gameStateChanged = true;
                        gameState = GAME_OVER;
                    }

                    float pass = wall[0] + mWallSprite.width + mKittySprite.width - mKittySprite.top;
                    if (pass < 0 && -pass <= mWallSprite.speed) {
                        mLevelSprite.score();
                    }
                }

                break;
            case GAME_OVER:
                // bird
                if (mKittySprite.top < mFloorSprite.top - mKittySprite.width) {
                    mKittySprite.fall();
                    if (mKittySprite.top >= mFloorSprite.top - mKittySprite.high) {
                        mKittySprite.top = mFloorSprite.top - (int) mKittySprite.high;
                    }
                } else {
                    flag = false;
                    //GameBirdActivity.instance.showMessage(level_value);
                    //gameState = GAME_MENU;
                    //initGame();
                }
                break;

        }

    }


    public void fly() {
        switch (gameState) {
            case GAME_MENU:
                gameStateChanged = true;
                gameState = GAMEING;

                startFly();

//					bird_v = bird_vUp;
//					break;
            case GAMEING:
                mKittySprite.rise();
                break;
            case GAME_OVER:
                //bird down
                if (mKittySprite.top >= mFloorSprite.top - mKittySprite.high) {
                    gameStateChanged = true;
                    gameState = GAME_MENU;
                    initGame();
                }

                break;
        }
    }

}
