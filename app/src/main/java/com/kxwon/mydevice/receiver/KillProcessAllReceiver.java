package com.kxwon.mydevice.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.kxwon.mydevice.bean.TaskInfo;

import java.util.List;

/**
 *      清理所有的进程
 **/
public class KillProcessAllReceiver extends BroadcastReceiver {

	// 清理的总共的进程个数
	int totalCount = 0;
	// 清理的进程的大小
	int killMem = 0;

	List<TaskInfo> taskInfos;

	@Override
	public void onReceive(Context context, Intent intent) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);


        //得到手机上面正在运行的进程
		List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();

		
		for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : appProcesses) {
			//杀死所有的进程
			activityManager.killBackgroundProcesses(runningAppProcessInfo.processName);
		}

		Toast.makeText(context, "清理完毕", Toast.LENGTH_SHORT).show();

		/*// 清理进程的集合
		taskInfos = TaskInfoParser.getTaskInfos(context);

		for (TaskInfo taskInfo : taskInfos) {

			totalCount++;
			killMem += taskInfo.getMemorySize();
			// 杀死进程 参数表示包名
			activityManager.killBackgroundProcesses(taskInfo.getPackageName());

		}

		Toast.makeText(context, "共清理了" + totalCount + "个进程,释放了"
				+ Formatter.formatFileSize(context, killMem)
				+ "内存", Toast.LENGTH_SHORT).show();*/
	}

}
