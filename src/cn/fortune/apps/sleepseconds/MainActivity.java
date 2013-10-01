package cn.fortune.apps.sleepseconds;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;


import cn.fortune.apps.sleepseconds.vibrate.Vibrate;

public class MainActivity extends Activity {
    private Button startSleep;
    private EditText sleepMinutes;
    private static SharedPreferences sharedPreferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initVarForOther();

        initUI();

        clickListener();


    }

    private void initVarForOther() {

        (new Vibrate()).init(this);

        sharedPreferences = getSharedPreferences("sleep_seconds_conf", MODE_PRIVATE);

    }

    private void clickListener() {

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

        sleepMinutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                NumberPicker numberPicker = new NumberPicker(MainActivity.this);
                numberPicker.setMaxValue(60);
                numberPicker.setMinValue(5);

                numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        sleepMinutes.setText(String.valueOf(newVal));

                    }
                });
                numberPicker.setWrapSelectorWheel(true);

                ((EditText) numberPicker.getChildAt(1)).setInputType(InputType.TYPE_NULL);

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).setTitle("休息分钟")
                        .setView(numberPicker).setPositiveButton("ok", null).create();
                alertDialog.show();

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
