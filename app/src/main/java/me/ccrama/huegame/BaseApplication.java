package me.ccrama.huegame;

import android.app.Application;

/**
 * Created by Carlos on 10/22/2017.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SettingsActivity.init(this);
    }
}
