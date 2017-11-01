package com.example.administrator.faceadd.boot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.view.WindowManager;

/**
 * Created by Administrator on 2017/9/14.
 */

public class Testboot {
    public static final String ACTION_REQUEST_SHUTDOWN = "android.intent.action.ACTION_REQUEST_SHUTDOWN";
    public void openBoot(Context context){
        PowerManager       PowerManagerpm =(PowerManager)context.getSystemService(Context.POWER_SERVICE);

        PowerManager.WakeLock wakeLock =PowerManagerpm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"My Tag");
        wakeLock.setReferenceCounted(false);
        wakeLock.acquire(30*1000);//30s亮屏
        wakeLock.release();//释放锁，灭屏

      //  wakeLock.release();
    }
    public void testScreen(Activity context){
      //  context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//亮屏
        context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//灭屏
    }
}
