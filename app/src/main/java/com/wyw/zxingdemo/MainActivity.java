package com.wyw.zxingdemo;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.ThumbnailUtils;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.camera.BitmapLuminanceSource;
import com.uuzuche.lib_zxing.decoding.DecodeFormatManager;

import java.util.Hashtable;
import java.util.Vector;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "MainActivity";
    /**
     * 调用摄像头拍摄扫描
     */
    private static final int REQUEST_CODE = 1005;
    /**
     * 打开相册扫描照片
     */
    private static final int REQUEST_IMAGE = 1006;


    private ImageView imageView;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.btn_clickMe);
        Button picBtn = (Button) findViewById(R.id.btn_pic);
        button.setOnClickListener(this);
        picBtn.setOnClickListener(this);

        imageView = (ImageView) findViewById(R.id.iv_pic);
        tvResult = (TextView) findViewById(R.id.tv_result);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_clickMe: {  //初级扫码操作
                if (isCameraUseable()) {
                    Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    Toast.makeText(this, "请开放摄像头权限，。", Toast.LENGTH_LONG).show();

                }
            }
            break;

            case R.id.btn_pic: { //读取本地图片扫描
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE);
            }
            break;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
        if (requestCode == REQUEST_IMAGE) { //打开系统相册扫描图片
            if (data != null) {

                String path = BitmapUtil.getImageAbsolutePath(this, data.getData());

                Log.i(TAG, "选中的图片路径是:" + path);
                CodeUtils.analyzeBitmap(path, new CodeUtils.AnalyzeCallback() {
                    @Override
                    public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                        imageView.setImageBitmap(mBitmap);
                        Toast.makeText(MainActivity.this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                        tvResult.setText(result);
                    }

                    @Override
                    public void onAnalyzeFailed() {
                        Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                    }
                });
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
