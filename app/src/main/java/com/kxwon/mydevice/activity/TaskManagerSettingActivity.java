package com.kxwon.mydevice.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.kxwon.mydevice.R;
import com.kxwon.mydevice.receiver.KillProcessService;
import com.kxwon.mydevice.util.SharedPreferencesUtils;
import com.kxwon.mydevice.util.SystemInfoUtils;


/**
 * 任务管理器的设置界面
 **/
public class TaskManagerSettingActivity extends Activity {

    private SharedPreferences sp;
    private CheckBox cb_status_kill_process;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        initUI();

    }

    private void initUI() {
        setContentView(R.layout.activity_task_manager_setting);
        CheckBox cb_status = (CheckBox) findViewById(R.id.cb_status);

        //设置是否选中
        cb_status.setChecked(SharedPreferencesUtils.getBoolean(TaskManagerSettingActivity.this, "is_show_system", false));

        cb_status.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferencesUtils.saveBoolean(TaskManagerSettingActivity.this, "is_show_system", isChecked);
            }
        });

        //定时清理进程

        cb_status_kill_process = (CheckBox) findViewById(R.id.cb_status_kill_process);

        final Intent intent = new Intent(this, KillProcessService.class);

        cb_status_kill_process.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    startService(intent);
                } else {
                    stopService(intent);
                }
            }
        });

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (SystemInfoUtils.isServiceRunning(TaskManagerSettingActivity.this, "com.itheima.mobileguard.services.KillProcessService")) {
            cb_status_kill_process.setChecked(true);
        } else {
            cb_status_kill_process.setChecked(false);
        }
    }


    /**
     * 返回
     * @param view
     */
    public void backToPrePage(View view){
        Intent intent = new Intent(TaskManagerSettingActivity.this,TaskManagerActivity.class);// 跳转
        startActivity(intent);
        finish();// 关闭当前界面

    }

    // 按系统返回键事件
    public boolean onKeyDown(int keyCode, KeyEvent event) {// 按键回调方法
        if (keyCode == KeyEvent.KEYCODE_BACK) {// 判断按键键值做出相应操作
            Intent intent = new Intent(TaskManagerSettingActivity.this,TaskManagerActivity.class);// 跳转
            startActivity(intent);
            finish();// 关闭当前界面
        }
        return false;
    }

}
