package app.songy.com.webdemo;

import android.app.Application;

/**
 * Description:
 * Created by song on 2018/8/9.
 * email：bjay20080613@qq.com
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        WebHelper.instanceView(getApplicationContext());
        if (WebHelper.getWebView()!=null) WebHelper.getWebView().loadUrl("file:///android_asset/native.html");
    }


}
