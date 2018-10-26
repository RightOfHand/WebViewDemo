package app.songy.com.webdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Description:
 * Created by song on 2018/8/10.
 * email：bjay20080613@qq.com
 */
public class IntroActivity extends BaseActivity {
//    private String webUrl="https://item.m.jd.com/ware/view.action?wareId=7652161&clickUrl=";
    private String webUrl="https://www.baidu.com/";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        TextView tvGo=(TextView) findViewById(R.id.tv_go);

        tvGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent=new Intent(IntroActivity.this,MainActivity.class);
               intent.putExtra("url",webUrl);
                startActivity(intent);
            }
        });
    }
}
