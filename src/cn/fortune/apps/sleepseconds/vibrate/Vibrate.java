package cn.fortune.apps.sleepseconds.vibrate;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.Log;

import cn.fortune.apps.sleepseconds.MainActivity;
import cn.fortune.apps.sleepseconds.helper.TimeToMillSeconds;

/**
 * Created by fortune on 13-10-1.
 */
public class Vibrate {
    private static long[] pattern = {100, 400, 100, 400};
    private static Vibrator vibrate;
    private static long endTime;
    private static Thread thread;

    public void init(Activity activity) {
        vibrate = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public Vibrate() {

    }

    public void setNoRepeat(long millisecond) {
        vibrate.vibrate(millisecond);
        Log.e("tag", "no repeat");
    }

    public void setRepeatOne() {

        setRepeat(-1);
        Log.e("tag", "repeat one");
    }

    public void setRepeatAlways() {
        setRepeat(2);
        Log.e("tag", "repeat always");

    }


    private void setRepeat(int repeat) {
        vibrate.vibrate(pattern, repeat);
    }

    public void cancel() {
        vibrate.cancel();
        thread.interrupt();
    }

    private SharedPreferences getSharedPreferences() {
       return MainActivity.getSharedPreferences();
    }

    public void baseConfToVibrate() {

        String mood = getSharedPreferences().getString("tipMood", "repeatAlways");


        Log.e("tag", mood);

        if (mood.equalsIgnoreCase("repeatAlways")) {
            this.setRepeatAlways();
        }

        if (mood.equalsIgnoreCase("setRepeatOne")) {
            this.setRepeatOne();
        }

        if (mood.equalsIgnoreCase("NoRepeat")) {
//            long millsTime = getSharedPreferences().getInt("repeatTime", 1) * 60 * 1000;
            long millsTime = 10 * 1000;
            this.setNoRepeat(millsTime);
        }

    }

    public void planVibrate() {
        Log.d("tag", "planVi");

        endTime = TimeToMillSeconds.addTimeToNow(getSharedPreferences().getInt("sleepMinutes", 1));
        thread = new PlanVibrateThread();
        thread.start();

    }


    class PlanVibrateThread extends Thread {
        @Override
        public void run() {
            long now ;
            while (true) {

                now = TimeToMillSeconds.nowOfMillSecond() ;
                Log.d("now", String.valueOf(now));
                if (now < endTime) {
                    Log.d("tag", "<");
                    Log.e("time", String.valueOf((endTime - now) / 60000));
                    SystemClock.sleep((endTime-now)/2);
                } else {
                    Log.d("tag", "> =");
                    baseConfToVibrate();
                   break;
                }
            }

        }
    }

}
