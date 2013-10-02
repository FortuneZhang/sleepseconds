package cn.fortune.apps.sleepseconds.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by fortune on 13-10-1.
 */
public class SharedPreferencesHelper {
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private static SharedPreferencesHelper sharedPreferencesHelper;

    public SharedPreferencesHelper() {

    }

    public void init(Activity activity) {
        sharedPreferences = activity.getSharedPreferences("sleep_seconds_conf", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public String getVibrateMood() {
        return sharedPreferences.getString("tipMood", "repeatAlways");
    }

    public void setVibrateMood(String mood) {
        editor.putString("tipMood", mood);
        editor.commit();
    }

    public int getTipTime() {
        return sharedPreferences.getInt("sleepMinutes", 10);
    }

    public void setTipTime(int tipTime) {
        editor.putInt("sleepMinutes", tipTime);
        editor.commit();
    }

    public static SharedPreferencesHelper getInstance() {
        if (sharedPreferencesHelper == null) {
            sharedPreferencesHelper = new SharedPreferencesHelper();
        }
        return sharedPreferencesHelper;
    }
}
