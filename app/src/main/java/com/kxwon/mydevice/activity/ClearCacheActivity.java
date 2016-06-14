package com.kxwon.mydevice.activity;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.kxwon.mydevice.R;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by KXwon on 2016/6/13.
 */
public class ClearCacheActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        setContentView(R.layout.activity_clean_cache);
        PackageManager packageManager = getPackageManager();
        /**
         * 接收2个参数
         * 第一个参数接收一个包名
         * 第二个参数接收aidl的对象
         */
//		  * @hide
//		     */
//		    public abstract void getPackageSizeInfo(String packageName,
//		            IPackageStatsObserver observer);
//		packageManager.getPackageSizeInfo();

        //安装到手机上面所有的应用程序
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);

        for (PackageInfo packageInfo : installedPackages) {
            getCacheSize(packageInfo);
        }


    }

    private void getCacheSize(PackageInfo packageInfo) {
        try {
            Class<?> clazz = getClassLoader().loadClass("packageManager");
            //通过反射获取到当前的方法
            //Method method = clazz.getDeclaredMethod("getPackageSizeInfo", String.class,IPackageStatsObserver.class);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
