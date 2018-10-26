package app.songy.com.webdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity {
    private static final String TAG=MainActivity.class.getSimpleName();
    private WebView mWebView;
    private static final String APP_CACAHE_DIRNAME = "/webcache";
    private static final String APP_DB_DIRNAME = "/webdb";
//    private String webUrl="https://www.baidu.com/";
    private String webUrl=null;
    private long startTime=0;
    private long createTime=0;
    private long endTime=0;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createTime=System.currentTimeMillis();
        Log.e(TAG, "onCreate:"+createTime);
        webUrl=getIntent().getStringExtra("url");
//        mWebView=(WebView) findViewById(R.id.wb_test);
        linearLayout=(LinearLayout) findViewById(R.id.ll_container);
        mWebView=WebHelper.getWebView();
        mWebView.loadUrl(webUrl);
        linearLayout.addView(mWebView);



//        init();
//        initClient();

    }

    private void toWeb(){
        Intent intent=new Intent(this,MainActivity.class);
        intent.putExtra("url","https://www.baidu.com/");
        startActivity(intent);
    }
    private void init(){
        WebSettings settings=mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //设置 缓存模式
        // 开启 DOM storage API 功能
        settings.setDomStorageEnabled(true);
        //开启 database storage API 功能
        settings.setDatabaseEnabled(true);
        String cacheDirPath = getExternalCacheDir().getAbsolutePath();
//      String cacheDirPath = getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME;
        //设置数据库缓存路径
        //设置  Application Caches 缓存目录
        settings.setAppCachePath(cacheDirPath);
        Log.i(TAG,"cache path：："+cacheDirPath);
        //开启 Application Caches 功能
        settings.setAppCacheEnabled(true);

        //自适应屏幕
        //noinspection deprecation
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        if (Build.VERSION.SDK_INT >= 21) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }
    private void initClient(){
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onLoadResource(WebView view, String url) {

                Log.i(TAG, "onLoadResource url="+url);

                super.onLoadResource(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webview, String url) {

                Log.i(TAG, "intercept url="+url);

                webview.loadUrl(url);

                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                startTime=System.currentTimeMillis();
                Log.e(TAG, "onCreateToStart:"+(startTime-createTime));


            }

            @Override
            public void onPageFinished(WebView view, String url) {
                endTime=System.currentTimeMillis();
                Log.e(TAG, "onStartToFinished:"+(endTime-startTime));

            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                Uri uri = request.getUrl();
                String path = uri.getPath();//仅路径，不带参数
//                Log.i(TAG,"Request  uri拦截路径uri：："+uri);
//                Log.i(TAG,"Request  uri拦截路径path：："+path);
//                if (uri.toString().contains("homead3.do")){//拦截到H5的网络请求
//                    try {
//                        String localTargetRes = FileUtils.INSTANCE.getSDPath()+Constant.LOCAL_FOLDER_ROOT_CONTENT_PATH + File.separator+ uri.getAuthority()+File.separator+"homead3.do";
//                        Log.d("cut","Request  替换的数据文本：："+localTargetRes);
//                        Map<String,String> map = new HashMap<>();
//                        map.put("Access-Control-Allow-Origin","*");
//                        map.put("Access-Control-Allow-Headers","Content-Type");
//                        WebResourceResponse resourceResponse = new WebResourceResponse("application/javascript", "UTF-8",200,"",map,new FileInputStream(new File(localTargetRes)));
//
//                        return resourceResponse;
//                    } catch (Exception e) {
//                        e.printStackTrace();
//
//                        return null;//异常情况，直接访问网络资源
//                    }
//                }

                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {


                Toast.makeText(getApplicationContext(), "",
                        Toast.LENGTH_LONG).show();
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

                Log.e(TAG, "onJsAlert " + message);

                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                result.confirm();

                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {

                Log.e(TAG, "onJsConfirm " + message);

                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {

                Log.e(TAG, "onJsPrompt " + url);

                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });

        mWebView.loadUrl(webUrl);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        linearLayout.removeAllViews();
//        mWebView.destroy();
    }

    /**
     * 清除WebView缓存
     */
    public void clearWebViewCache(){

        //清理Webview缓存数据库
        try {
            deleteDatabase("webview.db");
            deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //WebView 缓存文件
        File appCacheDir = new File(getFilesDir().getAbsolutePath()+APP_CACAHE_DIRNAME);
        Log.e(TAG, "appCacheDir path="+appCacheDir.getAbsolutePath());

        File webviewCacheDir = new File(getCacheDir().getAbsolutePath()+"/webviewCache");
        Log.e(TAG, "webviewCacheDir path="+webviewCacheDir.getAbsolutePath());

        //删除webview 缓存目录
        if(webviewCacheDir.exists()){
            deleteFile(webviewCacheDir);
        }
        //删除webview 缓存 缓存目录
        if(appCacheDir.exists()){
            deleteFile(appCacheDir);
        }
    }

    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    public void deleteFile(File file) {

        Log.i(TAG, "delete file path=" + file.getAbsolutePath());

        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
            Log.e(TAG, "delete file no exists " + file.getAbsolutePath());
        }
    }

}
