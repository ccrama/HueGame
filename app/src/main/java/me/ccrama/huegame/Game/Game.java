package me.ccrama.huegame.Game;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import me.ccrama.huegame.GameActivity;
import me.ccrama.huegame.R;
import me.ccrama.huegame.Tile;

/**
 * Created by Carlos on 10/22/2017.
 */

public class Game {
    public CountDownTimer timer;
    boolean invalid;
    public OnColorChange onColorChange;
    public OnLetterChange onLetterChange;
    public OnPlayerMove onPlayerMove;
    public boolean paused;

    public GameActivity bindTo;

    public HashMap<String, Tile> tileMap;

    public Game(GameActivity parent) {
        this.bindTo = parent;
        onColorChange = parent;
    }

    public void initialize() {
        //todo this
    }

    public void resume() {
        //todo this
    }

    public void didEnd() {
        //todo this
    }

    public void addTime(int time) {
        //todo this
    }

    public void changeColor(int newColor) {
        //todo this
    }

    public boolean getPowerup() {
         Random percent = new Random();
        return  percent.nextFloat() < 0.03;
    }

    public interface OnColorChange {
        void onColorChange(int newColor);

        void flashRed();
    }

    public interface OnLetterChange {
        void onColorChange(char newLetter);
    }

    public interface OnPlayerMove {
        void onPlayerMove(int oldX, int oldY, int newX, int newY);
    }

    public void setupInitialState() {
        tileMap = new HashMap<>();
        Tile center = new Tile();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String id = i + ":" + j;
                Tile t = new Tile();
                t.x = i;
                t.y = j;
                t.color = getRandomColor();
                t.text = getRandomChar();
                
                t.powerup = getPowerup();
                t.timeBonus = randInt(1, 31);
                
                tileMap.put(id, t);
                if(i == 1 && j == 1) {
                    center = t;
                }
            }
        }

        colorToGet = center.color;
        toGet = center.text;

        resetTilesWithOffset();
        updateUICenter();


    }

    public char toGet = getRandomChar();
    public int colorToGet = getRandomColor();

    public void updateUICenter() {
        Tile tile = tileMap.get((1 + bindTo.xOffset) + ":" + (1 + bindTo.yOffset));
        invalid = !((tile.text == toGet) || (tile.color == colorToGet));

        if(tile.powerup){
            tile.powerupUsed = true;
            timeLeft += (tile.timeBonus * 1000);
            tileMap.put((1 + bindTo.xOffset) + ":" + (1 + bindTo.yOffset), tile);
        }
    }

    public void resetTilesWithOffset() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int x = i + bindTo.xOffset;
                int y = j + bindTo.yOffset;
                Log.v("Hue", "X is " + x + " and y is " + y);
                Tile tile = tileMap.get(x + ":" + y);
                if (tile == null) {
                    tile = createNewTile(x, y);
                }
                TextView t = bindTo.textViews[((j) * 3) + (i)];
                tile.attachToView(t, bindTo.getResources());
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

        t.powerup = getPowerup();
        t.timeBonus = randInt(1, 31);

        tileMap.put(id, t);
        return t;
    }

    int timeLeft;

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
        timeLeft = 30000;
        startTimer();
    }

    public void startTimer() {
        timer = new CountDownTimer(timeLeft, 500) {

            public void onTick(long millisUntilFinished) {
                timeLeft -= 500;
                final long minute = TimeUnit.MILLISECONDS.toMinutes(timeLeft);
                final long second = TimeUnit.MILLISECONDS.toSeconds(timeLeft) - (60 * minute);
                if (minute > 0) {
                    bindTo.time.setText(String.format("%02d:%02d", minute, second));
                } else {
                    bindTo.time.setText(String.format("%02d:%02d", minute, second));
                }

                if (invalid) {
                    onColorChange.flashRed();
                    timeLeft -= 500;
                }
                if (timeLeft <= 0) {
                    timer.cancel();
                }

            }

            public void onFinish() {
                if (timeLeft > 0) {
                    startTimer();
                } else {
                    bindTo.finish();
                }
            }
        }.start();

    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}
