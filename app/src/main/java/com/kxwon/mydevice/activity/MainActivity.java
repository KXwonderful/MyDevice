package com.kxwon.mydevice.activity;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.kxwon.mydevice.service.FloatWindowService;
import com.kxwon.mydevice.view.MyDialog;
import com.kxwon.mydevice.R;
import com.kxwon.mydevice.receiver.AdminReceiver;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity implements View.OnClickListener{

    private Button btn_activateDevice;   //激活设备
    private Button btn_createIcon;       //创建桌边快捷图标
    private Button btn_openRocket;       //打开火箭
    private Button btn_closeRocket;      //关闭火箭
    private Button btn_lockDevice;       //一键锁屏
    private Button btn_restartDevice;    //一键重启
    private Button btn_closeDevice;      //一键关机
    private Button btn_clearData;        //清除数据
    private Button btn_uninstall;        //清除数据
    private Button btn_appManage;        //软件管理
    private Button btn_processManage;    //进程管理

    public static final String ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";

    private DevicePolicyManager mDPM;
    private ComponentName mDeviceAdminSample;

    private MyDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initLayout();

        mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);// 获取设备策略服务
        mDeviceAdminSample = new ComponentName(this, AdminReceiver.class);// 设备管理组件
    }

    private void initLayout() {

        btn_openRocket = (Button) findViewById(R.id.btn_openRocket);
        btn_closeRocket = (Button) findViewById(R.id.btn_closeRocket);
        btn_activateDevice = (Button) findViewById(R.id.btn_activateDevice);
        btn_createIcon = (Button) findViewById(R.id.btn_createIcon);
        btn_lockDevice = (Button) findViewById(R.id.btn_lockDevice);
        btn_restartDevice = (Button) findViewById(R.id.btn_restartDevice);
        btn_closeDevice = (Button) findViewById(R.id.btn_closeDevice);
        btn_clearData = (Button) findViewById(R.id.btn_clearData);
        btn_uninstall = (Button) findViewById(R.id.btn_uninstall);
        btn_appManage = (Button) findViewById(R.id.btn_appManage);
        btn_processManage = (Button) findViewById(R.id.btn_processManage);

        btn_openRocket.setOnClickListener(this);
        btn_closeRocket.setOnClickListener(this);
        btn_activateDevice.setOnClickListener(this);
        btn_createIcon.setOnClickListener(this);
        btn_lockDevice.setOnClickListener(this);
        btn_restartDevice.setOnClickListener(this);
        btn_closeDevice.setOnClickListener(this);
        btn_clearData.setOnClickListener(this);
        btn_uninstall.setOnClickListener(this);
        btn_appManage.setOnClickListener(this);
        btn_processManage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_activateDevice:
                //激活设备
                activeAdmin();
                break;

            case R.id.btn_createIcon:
                //创建图标
                createShortcut();
                break;

            case R.id.btn_lockDevice:
                //一键锁屏
                lockScreen();
                break;

            case R.id.btn_openRocket:
                //打开火箭
                openRocket();
                break;

            case R.id.btn_closeRocket:
                //关闭火箭
                closeRocket();
                break;

            case R.id.btn_restartDevice:
                //一键重启
                RestartDevice();
                break;

            case R.id.btn_closeDevice:
                //一键关机
                CloseDevice() ;
                break;

            case R.id.btn_appManage:
                //软件管理
                AppManager() ;
                break;

            case R.id.btn_processManage:
                //进程管理
                ProcessManager() ;
                break;

            case R.id.btn_clearData:
                //清除数据
                clearData();
                break;

            case R.id.btn_uninstall:
                //卸载软件
                unInstall();
                break;

            default:
                break;
        }
    }

    /**
     * 激活设备管理器, 也可以在设置->安全->设备管理器中手动激活
     */
    public void activeAdmin() {
        if (mDPM.isAdminActive(mDeviceAdminSample)) {// 判断设备管理器是否已经激活
           Toast.makeText(this,"已激活设备管理器",Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                    mDeviceAdminSample);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                    "哈哈哈, 我们有了wonderful设备管理器, 好NB!");
            startActivity(intent);
        }

    }

    /**
     * 创建桌面图标
     */
    public void createShortcut() {

        Intent addIntent = new Intent(ACTION_ADD_SHORTCUT);

        // 获取快捷键的图标
        Parcelable icon = Intent.ShortcutIconResource.fromContext(this, R.mipmap.lock_device);
        Intent myIntent = new Intent(this, LockDevice.class);

        // 允许重复创建
        addIntent.putExtra("duplicate", true);
        // 快捷方式的标题
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "一键锁屏");
        // 快捷方式的图标
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        // 快捷方式的动作
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, myIntent);

        // 发送广播
        sendBroadcast(addIntent);

        Toast.makeText(this, "创建桌面快捷方式完成", Toast.LENGTH_SHORT).show();
    }


   /* public void CreateShotCut(final Context context, final Class<?> clazz,
                              final String name) {

        Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
        // 加入action,和category之后，程序卸载的时候才会主动将该快捷方式也卸载
        shortcutIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        shortcutIntent.setClass(context, clazz);
        *//**
         * 创建一个Bundle对象让其保存将要传递的值
         *//*
        *//*Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        shortcutIntent.putExtras(bundle);*//*
        *//**
         * 设置这条属性，可以使点击快捷方式后关闭其他的任务栈的其他activity，然后创建指定的acticity
         *//*
        shortcutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // 创建快捷方式的Intent
        Intent shortcut = new Intent(Intent.ACTION_CREATE_SHORTCUT);
        // 不允许重复创建
        shortcut.putExtra("duplicate", false);
        // 点击快捷图片，运行的程序主入口
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        // 需要现实的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);

        // 快捷图片
        Parcelable icon = Intent.ShortcutIconResource.fromContext(
                getApplicationContext(),R.mipmap.lock_device);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        shortcut.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        context.sendBroadcast(shortcut);
    }*/


    /**
     *  一键锁屏
     */
    public void lockScreen() {
        if (mDPM.isAdminActive(mDeviceAdminSample)) {// 判断设备管理器是否已经激活
            mDPM.lockNow();// 立即锁屏
            //mDPM.resetPassword("1234", 0); //锁屏后设置密码
        } else {
            Toast.makeText(this, "必须先激活设备管理器!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 打开火箭
     */
    public void openRocket(){
        startService(new Intent(this, FloatWindowService.class));
        //finish();
    }

    /**
     * 关闭火箭
     */
    public void closeRocket(){
        stopService(new Intent(this, FloatWindowService.class));
        //finish();
    }

    /**
     *  一键重启
     */
    public void RestartDevice() {
    }


    /**
     * 一键关机
     */
    public void CloseDevice(){
    }

    /**
     * 软件管理
     */
    public void AppManager(){
        Intent intent=new Intent(MainActivity.this,AppManagerActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 进程管理
     */
    public void ProcessManager(){
        Intent intent=new Intent(MainActivity.this,TaskManagerActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 清除手机数据，恢复出厂设置
     */
    public void clearData() {
        if (mDPM.isAdminActive(mDeviceAdminSample)) {// 判断设备管理器是否已经激活
            mDialog = new MyDialog();
            mDialog.showDialog(MainActivity.this, "清除数据",
                    "警告：清除数据将会把手机还原成出厂设置，后果自负",
                    new ClearListener());
        } else {
            Toast.makeText(this, "必须先激活设备管理器!", Toast.LENGTH_SHORT).show();
        }
    }

    //对话框点击事件监听
    private class ClearListener implements MyDialog.IDialog {
        @Override
        public void confirm() {
            //mDPM.wipeData(0);// 清除数据,恢复出厂设置
            Toast.makeText(MainActivity.this,"已屏蔽该功能",Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 卸载本软件
     */
    public void unInstall() {

        MyDialog.showDialog(MainActivity.this,"卸载软件","是否卸载软件",new DialogListener());
    }

    //对话框点击事件监听
    private class DialogListener implements MyDialog.IDialog {
        @Override
        public void confirm() {
            mDPM.removeActiveAdmin(mDeviceAdminSample);// 取消激活

            // 卸载程序
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        }
    }

     /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            exitBy2Click();      //调用双击退出函数
        }
        return false;
    }


    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;
    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }

}
