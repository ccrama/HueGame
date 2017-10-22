package me.ccrama.huegame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
}
