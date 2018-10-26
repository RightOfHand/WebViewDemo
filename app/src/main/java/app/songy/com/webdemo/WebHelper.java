package app.songy.com.webdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Description:
 * Created by song on 2018/8/9.
 * email：bjay20080613@qq.com
 */
public class WebHelper {
    private static final String TAG=WebHelper.class.getSimpleName();
    private static final String APP_CACAHE_DIRNAME = "/webcache";
    private static final String APP_DB_DIRNAME = "/webdb";
    private static long startTime=0;
    private static long endTime=0;
    private static WebView mWebView;


//    public static void instanceView(Context context){
//        mWebView=new WebView(context);
//        init(context);
//        initClient();
//    }
    public static void instanceView(Context context){
        mWebView=new WebView(context);
        mWebView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        WebSettings settings=mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //设置 缓存模式
        // 开启 DOM storage API 功能
        settings.setDomStorageEnabled(true);
        //开启 database storage API 功能
        settings.setDatabaseEnabled(true);
        String cacheDirPath = context.getExternalCacheDir().getAbsolutePath();
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
        initClient();
    }
    private static void initClient(){
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
                Log.e(TAG, "onConfigStarted:"+startTime);


            }

            @Override
            public void onPageFinished(WebView view, String url) {
                endTime=System.currentTimeMillis();
                Log.e(TAG, "onConfigFinished:"+(endTime-startTime));

            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
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


            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

                Log.e(TAG, "onJsAlert " + message);


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
//       mWebView.loadUrl("file:///android_asset/native.html");
       mWebView.loadUrl("file:///android_asset/native.html");
    }

    public static WebView getWebView(){

        return mWebView;
    }
}
