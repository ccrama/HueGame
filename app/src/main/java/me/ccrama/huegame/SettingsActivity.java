package me.ccrama.huegame;

import android.app.Application;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {

    public static boolean highContrastEnabled = false;
    public static boolean soundsEnabled = false;
    public static boolean buttonModeEnabled = false;

    public static String HIGH_CONTRAST = "prefHighContrast";
    public static String SOUNDS_ENABLED = "prefSounds";
    public static String BUTTON_MODE = "prefButtonMode";

    public static SharedPreferences prefs;

    public static void init(Application a){
        prefs = a.getSharedPreferences("settings", 0);
        highContrastEnabled = prefs.getBoolean(HIGH_CONTRAST, false);
        soundsEnabled = prefs.getBoolean(SOUNDS_ENABLED, true);
        buttonModeEnabled = prefs.getBoolean(BUTTON_MODE, false);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }
}
