package cn.fortune.apps.sleepseconds;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;


import cn.fortune.apps.sleepseconds.helper.SharedPreferencesHelper;
import cn.fortune.apps.sleepseconds.vibrate.Vibrate;

public class MainActivity extends Activity {
    private Button startSleep;
    private EditText configSleepMinutes;
    private static SharedPreferences sharedPreferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initVarForOther();

        initUI();

        listener();


    }

    private void initVarForOther() {

        (new Vibrate()).init(this);

        sharedPreferences = getSharedPreferences("sleep_seconds_conf", MODE_PRIVATE);
        (new SharedPreferencesHelper()).init(this);

    }

    private void listener() {

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

        configSleepMinutes.setInputType(InputType.TYPE_NULL);

        configSleepMinutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                NumberPicker numberPicker = new NumberPicker(MainActivity.this);
                numberPicker.setMaxValue(60);
                numberPicker.setMinValue(5);

                numberPicker.setValue(Integer.parseInt(configSleepMinutes.getText().toString()));

                numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        configSleepMinutes.setText(String.valueOf(newVal));

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
        configSleepMinutes = (EditText) findViewById(R.id.sleepMinutes);
    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    private void recordSleepMinute() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt("sleepMinutes", Integer.parseInt(configSleepMinutes.getText().toString()));
        editor.putInt("sleepMinutes", 1);
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(Menu.NONE, Menu.FIRST + 1, 1, "退出"); //setIcon
        menu.add(Menu.NONE, Menu.FIRST + 2, 2, "设置");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case  Menu.FIRST + 1 :
                quit();
                break;
            case  Menu.FIRST + 2 :
                goToSettingActivity();
                break;
            default:
                break;



        }

        return super.onOptionsItemSelected(item);
    }

    private void quit() {
        System.exit(0);
    }

    private void goToSettingActivity() {

        Log.d("jump", "jump");
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, SettingActivity.class);
        startActivity(intent);
        finish();

    }
}
