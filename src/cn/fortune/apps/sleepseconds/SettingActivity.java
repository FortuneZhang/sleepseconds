package cn.fortune.apps.sleepseconds;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import cn.fortune.apps.sleepseconds.helper.SharedPreferencesHelper;
import cn.fortune.apps.sleepseconds.vibrate.Vibrate;

/**
 * Created by fortune on 13-10-1.
 */
public class SettingActivity extends Activity {

    private RadioGroup radioGroup;
    private RadioButton noRepeat, repeatOne, repeatAlways;

    public void onCreate(Bundle savedInstanceState) {
        Log.d("settingActivity", "invoke");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        initUi();

        renderUi();

        uiListener();
    }

    private void uiListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper();
                if (checkedId == noRepeat.getId()) {
                    sharedPreferencesHelper.setVibrateMood("noRepeat");
                }
                if (checkedId == repeatOne.getId()) {
                    sharedPreferencesHelper.setVibrateMood("repeatOne");
                }

                if (checkedId == repeatAlways.getId()) {
                    sharedPreferencesHelper.setVibrateMood("repeatAlways");
                }
                goToMainActivity();
            }
        });
    }

    private void initUi() {

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        noRepeat = (RadioButton) findViewById(R.id.noRepeat);
        repeatOne = (RadioButton) findViewById(R.id.repeatOne);
        repeatAlways = (RadioButton) findViewById(R.id.repeatAlways);
    }

    private void renderUi() {
        String mood = SharedPreferencesHelper.getInstance().getVibrateMood();

        if (mood.equalsIgnoreCase("noRepeat")) {
            noRepeat.setChecked(true);
        } else if (mood.equalsIgnoreCase("repeatOne")) {
            repeatOne.setChecked(true);
        } else if (mood.equalsIgnoreCase("repeatAlways")) {
            repeatAlways.setChecked(true);
        } else {
            repeatOne.setChecked(true);
        }


    }

    private void goToMainActivity() {
        Intent intent = new Intent();
        intent.setClass(SettingActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.KEYCODE_BACK == keyCode) {
            goToMainActivity();
        }
        return super.onKeyDown(keyCode, event);
    }
}