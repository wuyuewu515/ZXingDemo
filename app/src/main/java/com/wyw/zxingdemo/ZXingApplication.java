package com.wyw.zxingdemo;

import android.app.Application;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * 项目名称：ZXingDemo
 * 类描述：初始化zing工具类
 * 创建人：伍跃武
 * 创建时间：2017/5/18 15:21
 */
public class ZXingApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ZXingLibrary.initDisplayOpinion(this);
    }

}
