package com.kxwon.mydevice;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

/**
 * 一键锁屏
 * Created by KXwon on 2016/5/25.
 */
public class LockDevice extends Activity {

    private DevicePolicyManager mDPM;
    private ComponentName mDeviceAdminSample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);// 获取设备策略服务
        mDeviceAdminSample = new ComponentName(this, AdminReceiver.class);// 设备管理组件

        if (mDPM.isAdminActive(mDeviceAdminSample)) {// 判断设备管理器是否已经激活
            mDPM.lockNow();// 立即锁屏
            //mDPM.resetPassword("1234", 0);
        } else {
            Toast.makeText(this, "必须先激活设备管理器!", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
