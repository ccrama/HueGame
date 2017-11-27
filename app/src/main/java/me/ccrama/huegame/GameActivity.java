package me.ccrama.huegame;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
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

public class GameActivity extends AppCompatActivity {

    public GridLayout gridView;
    public Game game;
    public TextView time, center;
    public View background;

    public TextView[] textViews = new TextView[10];
    public HashMap<String, Tile> tileMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gridView = findViewById(R.id.gameGrid);
        time = findViewById(R.id.time);

        center = (TextView) findViewById(R.id.center);
        background = findViewById(R.id.background);

        center.setText("" + toGet);
        background.setBackgroundColor(getResources().getColor(colorToGet));

        final ViewTreeObserver vto = gridView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                gridView.getViewTreeObserver().removeGlobalOnLayoutListener(this);


                int width = -30 + gridView.getWidth() / 3;
                int height = -30 + gridView.getHeight() / 3;

                Log.v("Hue", "Width is " + width);

                for (int i = 0; i < 10; i++) {
                    TextView v = (TextView) getLayoutInflater().inflate(R.layout.tile, gridView, false);
                    v.getLayoutParams().width = width;
                    v.getLayoutParams().height = height;

                    gridView.addView(v);
                    textViews[i] = v;
                }
                setupInitialState();


            }
        });

        setupTimer();

        mDetector = new GestureDetectorCompat(this, new SwipeGestureDetector());


    }

    private GestureDetectorCompat mDetector;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void flashRed(){
        Animation animation = new AlphaAnimation(1, 0); // Change alpha
        // from fully
        // visible to
        // invisible
        animation.setDuration(500); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter
        // animation
        // rate
        // infinitely
        animation.setRepeatMode(Animation.REVERSE); // Reverse animation at

        // the
        // end so the layout will
        // fade back in
        background.startAnimation(animation);
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
            resetTilesWithOffset();
            updateUICenter();
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

    int xOffset, yOffset;

    boolean invalid;

    public void setupInitialState() {
        tileMap = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String id = i + ":" + j;
                Tile t = new Tile();
                t.x = i;
                t.y = j;
                t.color = getRandomColor();
                t.text = getRandomChar();
                tileMap.put(id, t);
            }
        }

        resetTilesWithOffset();
        updateUICenter();


    }

    char toGet = getRandomChar();
    int colorToGet = getRandomColor();

    public void updateUICenter() {
        Tile tile = tileMap.get((1 + xOffset) + ":" + (1 + yOffset));
        Log.v("Hue", "Title text is " + tile.text + "  adn comparing to " + toGet);
        invalid = !((tile.text == toGet)|| (tile.color == colorToGet));
    }

    public void resetTilesWithOffset() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int x = i + xOffset;
                int y = j + yOffset;
                Log.v("Hue", "X is " + x + " and y is " + y);
                Tile tile = tileMap.get(x + ":" + y);
                if (tile == null) {
                    tile = createNewTile(x, y);
                }
                TextView t = textViews[((j) * 3) + (i)];
                tile.attachToView(t, getResources());
            }
        }
    }

    public Tile createNewTile(int x, int y) {
        String id = x + ":" + y;
        Tile t = new Tile();
        t.x = x;
        t.y = y;
        t.color = getRandomColor();
        t.text = getRandomChar();
        tileMap.put(id, t);
        return t;
    }

    int timeLeft;
    Timer t;

    public static char getRandomChar() {
        char[] chars = new char[]{'A', 'B', '$', '1', '2', '3', 'X', 'Z', 'Y', '*', '&'};
        char random = chars[new Random().nextInt(chars.length)];
        return random;

    }

    public static int getRandomColor() {
        int[] colors = new int[]{R.color.md_amber_300, R.color.md_blue_300, R.color.md_blue_grey_500, R.color.md_red_500, R.color.md_green_300, R.color.md_deep_orange_400, R.color.md_pink_300, R.color.md_teal_800, R.color.md_yellow_500};
        int random = colors[new Random().nextInt(colors.length)];
        return random;
    }

    public void setupTimer() {
        timeLeft = 120000;

        new CountDownTimer(timeLeft, 1000) {

            public void onTick(long millisUntilFinished) {
                timeLeft -= 1000;
                final long minute = TimeUnit.MILLISECONDS.toMinutes(timeLeft);
                final long second = TimeUnit.MILLISECONDS.toSeconds(timeLeft) - (60 * minute);
                if (minute > 0) {
                    time.setText(String.format("%02d:%02d", minute, second));
                } else {
                    time.setText(String.format("%02d:%02d", minute, second));
                }

                if(invalid){
                    flashRed();
                }

            }

            public void onFinish() {
                finish();
            }
        }.start();


    }

    public void timeIncremented() {
        timeLeft -= 100;


    }

    public void resumeGame(String lastGameID) {
        //todo this
    }

    public void newGame() {
        //todo this
    }

}
