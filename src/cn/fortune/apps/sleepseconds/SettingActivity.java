package cn.fortune.apps.sleepseconds;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
    }
}