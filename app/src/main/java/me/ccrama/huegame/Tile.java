package me.ccrama.huegame;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

/**
 * Created by Carlos on 11/26/2017.
 */

public class Tile  {
    public char text;
    public int color;
    public int x, y;
    public boolean powerup;
    public boolean powerupUsed;
    public int timeBonus;

    public void attachToView(TextView v, Resources resources){
        v.setText(""  + (((powerup && !powerupUsed) ? "+" + timeBonus : text)));
        if(SettingsActivity.highContrastEnabled){
            v.setBackgroundColor(color);
        } else {
            v.setBackgroundColor(resources.getColor(color));
        }
    }
}
