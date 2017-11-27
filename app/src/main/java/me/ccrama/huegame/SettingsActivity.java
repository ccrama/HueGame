package me.ccrama.huegame;

import android.app.Application;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;

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

        SwitchCompat contrast = findViewById(R.id.contrast);
        contrast.setChecked(SettingsActivity.highContrastEnabled);
        contrast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean enabled) {
                highContrastEnabled = enabled;
                prefs.edit().putBoolean(HIGH_CONTRAST, enabled).apply();
            }
        });

        SwitchCompat sound = findViewById(R.id.sound);
        sound.setChecked(SettingsActivity.soundsEnabled);
        sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean enabled) {
                soundsEnabled = enabled;
                prefs.edit().putBoolean(SOUNDS_ENABLED, enabled).apply();
            }
        });

        SwitchCompat button = findViewById(R.id.button);
        button.setChecked(SettingsActivity.buttonModeEnabled);
        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean enabled) {
                buttonModeEnabled = enabled;
                prefs.edit().putBoolean(BUTTON_MODE, enabled).apply();
            }
        });
    }
}
