package me.ccrama.huegame;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {

    public static SharedPreferences scores;
    public TextView highScore;
    public int score = 0;
    public String lastGameID = "";

    @Override
    protected void onResume() {
        super.onResume();

        //Need to save lastGameID in the game screen because this will reload when the game is paused
        lastGameID = scores.getString("lastGame", "");
        updateUI();
    }

    public void updateUI(){
        highScore.setText(String.format("%d", score));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);
        scores = getSharedPreferences("scores", 0);

        //This TextView doesn't exist yet
        highScore = (TextView)findViewById(R.id.highscore);
    }

    public void resumeGame(String lastGameID){
        //todo this
    }

    public void newGame(){
        //todo this
    }

    public void openSettings(){
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }

    public void exitGame(){
        this.finish();
        System.exit(0);
    }
}
