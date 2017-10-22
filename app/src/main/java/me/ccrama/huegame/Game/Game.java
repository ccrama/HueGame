package me.ccrama.huegame.Game;

import java.util.Timer;

/**
 * Created by Carlos on 10/22/2017.
 */

public class Game {
    public Timer timer;
    public OnColorChange onColorChange;
    public OnLetterChange onLetterChange;
    public OnPlayerMove onPlayerMove;
    public Character currentLetter;
    public int currentColor;
    public int currentTimeMillis;
    public int elapsedTimeMillis;
    public boolean paused;

    public void initialize(){
        //todo this
    }

    public void resume(){
        //todo this
    }

    public void didEnd(){
        //todo this
    }

    public void addTime(int time){
        //todo this
    }

    public void changeColor(int newColor){
        //todo this
    }

    public interface OnColorChange{
        void onColorChange(int newColor);
    }

    public interface OnLetterChange{
        void onColorChange(char newLetter);
    }

    public interface OnPlayerMove{
        void onPlayerMove(int oldX, int oldY, int newX, int newY);
    }

}
