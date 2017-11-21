package me.ccrama.huegame;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import me.ccrama.huegame.Game.Game;

public class GameActivity extends AppCompatActivity {

    public GameGridView gridView;
    public Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    public void resumeGame(String lastGameID){
        //todo this
    }

    public void newGame(){
        //todo this
    }

    public class GameGridView extends GridView {

        public GameGridView(Context context) {
            super(context);
        }
    }
}
