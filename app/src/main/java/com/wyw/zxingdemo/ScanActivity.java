package com.wyw.zxingdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * 项目名称：ZXingDemo
 * 类描述： 自定义扫描界面
 * 创建人：伍跃武
 * 创建时间：2017/5/22 14:10
 */
public class ScanActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_layout);

        initView();
        initData();
    }

    private void initData() {


        CodeUtils.AnalyzeCallback callback = new AbsAnalyzeCallback(this) {
            @Override
            protected void AbsAnalyzeSuccess(Bitmap mBitmap, String result) {
                Intent resultIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
                bundle.putString(CodeUtils.RESULT_STRING, result);
                resultIntent.putExtras(bundle);
                ScanActivity.this.setResult(RESULT_OK, resultIntent);
                ScanActivity.this.finish();
            }
        };

        //执行扫面Fragment的初始化操作
        CaptureFragment captureFragment = new CaptureFragment();

        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.fragment_scan);

        captureFragment.setAnalyzeCallback(callback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();

    }

    private void initView() {
        Button btn_cancle = (Button) findViewById(R.id.btn_cancel);
        btn_cancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) { //退出扫描操作
        finish();
    }
}
