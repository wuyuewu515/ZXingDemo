package com.wyw.zxingdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * 项目名称：ZXingDemo
 * 类描述：
 * 创建人：伍跃武
 * 创建时间：2017/5/22 14:50
 */
public abstract class AbsAnalyzeCallback implements CodeUtils.AnalyzeCallback {

    private Context context;

    public AbsAnalyzeCallback(Context context) {
        this.context = context;
    }

    @Override
    public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
        AbsAnalyzeSuccess(mBitmap, result);
    }

    protected abstract void AbsAnalyzeSuccess(Bitmap mBitmap, String result);

    @Override
    public void onAnalyzeFailed() {
        Toast.makeText(context, "解析二维码失败", Toast.LENGTH_LONG).show();
    }
}
