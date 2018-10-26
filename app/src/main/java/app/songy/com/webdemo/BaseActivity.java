package app.songy.com.webdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import app.songy.com.webdemo.manager.ActivityManager;

/**
 * Description:
 * Created by song on 2018/8/16.
 * email：bjay20080613@qq.com
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.create(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.destroy(this);
    }
}
