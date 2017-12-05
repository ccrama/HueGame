package me.ccrama.huegame;

import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import me.ccrama.huegame.Game.Game;

public class GameActivity extends AppCompatActivity implements Game.OnColorChange, Game.OnLetterChange {

    public GridLayout gridView;
    public Game game;
    public TextView time, center;
    public View background, dot, parent;
    public TextView[] textViews = new TextView[10];

    public MediaPlayer backgroundM;
    public long startTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gridView = findViewById(R.id.gameGrid);
        time = findViewById(R.id.time);
        startTime = System.currentTimeMillis();
        parent = findViewById(R.id.parent);

        backgroundM = MediaPlayer.create(getApplicationContext(), R.raw.background);
        if(SettingsActivity.soundsEnabled) {
            backgroundM.start();
        }


        dot = findViewById(R.id.dot);
        center = (TextView) findViewById(R.id.center);
        background = findViewById(R.id.background);
        game = new Game(this);

        if(SettingsActivity.buttonModeEnabled){
            findViewById(R.id.buttons).setVisibility(View.VISIBLE);
            findViewById(R.id.left).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    xOffset -=1;
                    if(SettingsActivity.soundsEnabled) {
                        MediaPlayer.create(getApplicationContext(), R.raw.swipe).start();
                    }

                    game.resetTilesWithOffset();
                    game.updateUICenter();
                }
            });
            findViewById(R.id.right).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    xOffset +=1;
                    if(SettingsActivity.soundsEnabled) {
                        MediaPlayer.create(getApplicationContext(), R.raw.swipe).start();
                    }

                    game.resetTilesWithOffset();
                    game.updateUICenter();
                }
            });
            findViewById(R.id.up).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    yOffset -=1;
                    if(SettingsActivity.soundsEnabled) {
                        MediaPlayer.create(getApplicationContext(), R.raw.swipe).start();
                    }

                    game.resetTilesWithOffset();
                    game.updateUICenter();
                }
            });
            findViewById(R.id.down).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    yOffset +=1;
                    if(SettingsActivity.soundsEnabled) {
                        MediaPlayer.create(getApplicationContext(), R.raw.swipe).start();
                    }

                    game.resetTilesWithOffset();
                    game.updateUICenter();
                }
            });
        }


        final ViewTreeObserver vto = gridView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                gridView.getViewTreeObserver().removeGlobalOnLayoutListener(this);


                int width = -dpToPxHorizontal(15) + (gridView.getWidth() / 3);
                int height = -dpToPxVertical(20) + (gridView.getHeight() / 3);


                for (int i = 0; i < 10; i++) {
                    TextView v = (TextView) getLayoutInflater().inflate(R.layout.tile, gridView, false);
                    v.getLayoutParams().width = width;
                    v.getLayoutParams().height = height;

                    gridView.addView(v);
                    textViews[i] = v;
                }
                game.setupInitialState(GameActivity.this);
                game.setupTimer();
                center.setText("" +game.toGet);
                if(SettingsActivity.highContrastEnabled){
                    background.setBackgroundColor(game.colorToGet);
                } else {
                    background.setBackgroundColor(getResources().getColor(game.colorToGet));
                }


            }
        });

        mDetector = new GestureDetectorCompat(this, new SwipeGestureDetector());
    }

    private GestureDetectorCompat mDetector;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void flashRed(){
        Animation animation = new AlphaAnimation(1, 0.5f);
        animation.setDuration(250);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setRepeatMode(Animation.REVERSE);
        parent.startAnimation(animation);
        if(SettingsActivity.soundsEnabled) {
            MediaPlayer.create(getApplicationContext(), R.raw.alarm).start();
        }
    }

    public static int dpToPxHorizontal(int dp) {
        final DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int dpToPxVertical(int dp) {
        final DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.ydpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @Override
    public void onColorChange(int newColor) {

    }

    @Override
    public void onColorChange(char newLetter) {

    }

    class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2,
                               float velocityX, float velocityY) {

            switch (getSlope(e1.getX(), e1.getY(), e2.getX(), e2.getY())) {
                case 1:
                    yOffset += 1;
                    break;
                case 2:
                    xOffset += 1;

                    break;
                case 3:
                    yOffset -= 1;

                    break;
                case 4:
                    xOffset -= 1;

                    break;
            }
            if(SettingsActivity.soundsEnabled) {
                MediaPlayer.create(getApplicationContext(), R.raw.swipe).start();
            }

            game.resetTilesWithOffset();
            game.updateUICenter();
            return false;
        }

        private int getSlope(float x1, float y1, float x2, float y2) {
            Double angle = Math.toDegrees(Math.atan2(y1 - y2, x2 - x1));
            if (angle > 45 && angle <= 135)
                // top
                return 1;
            if (angle >= 135 && angle < 180 || angle < -135 && angle > -180)
                // left
                return 2;
            if (angle < -45 && angle >= -135)
                // down
                return 3;
            if (angle > -45 && angle <= 45)
                // right
                return 4;
            return 0;
        }
    }

    public int xOffset;
    public int yOffset;

    public void resumeGame(String lastGameID) {
        //todo this
    }

    public void newGame() {
        //todo this
    }
}
