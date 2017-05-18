package com.wyw.zxingdemo;

import android.content.Intent;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE = 1005;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.btn_clickMe);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (isCameraUseable()) {
            Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            Toast.makeText(this, "请开放摄像头权限，。", Toast.LENGTH_LONG).show();

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //  super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null)
                    return;
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    public boolean isCameraUseable() {

        boolean canUse = true;

        Camera mCamera = null;

        try {

            mCamera = Camera.open();

// setParameters 是针对魅族MX5。MX5通过Camera.open()拿到的Camera对象不为null

            Camera.Parameters mParameters = mCamera.getParameters();

            mCamera.setParameters(mParameters);

        } catch (Exception e) {

            canUse = false;

        }

        if (mCamera != null) {

            mCamera.release();

        }
        return canUse;
    }
}
