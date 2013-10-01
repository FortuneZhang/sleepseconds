package cn.fortune.apps.sleepseconds;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import cn.fortune.apps.sleepseconds.vibrate.Vibrate;

public class MainActivity extends Activity
{
    private Button startSleep;
    private EditText sleepMinutes;
    private static SharedPreferences sharedPreferences;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initVarForOther();

        initUI();

        btnClickListener();


    }

    private void initVarForOther() {

        (new Vibrate()).init(this);

        sharedPreferences =  getSharedPreferences("sleep_seconds_conf", MODE_PRIVATE);

    }

    private void btnClickListener() {

        final Activity activity = this;

        startSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordSleepMinute();

                if (startSleep.getText().toString().equalsIgnoreCase("开始")) {
                    startSleep.setText("结束");
                    (new Vibrate()).planVibrate();
                } else {
                    (new Vibrate()).cancel();
                    startSleep.setText("开始");
                }
            }
        });
    }


    private void initUI() {

        startSleep = (Button) findViewById(R.id.startSleep);
        sleepMinutes = (EditText) findViewById(R.id.sleepMinutes);
    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    private void recordSleepMinute() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("sleepMinutes", Integer.parseInt(sleepMinutes.getText().toString()));
    }
}
