package com.example.administrator.faceadd;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by Administrator on 2017/9/14.
 */

public class MyAccessibilityService extends AccessibilityService {
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        System.out.println("====re==========");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        int  c=event.getAction();
        System.out.println("============c=="+c);
        System.out.println("============c=="+event.toString());
        System.out.println("============c=="+event.getContentChangeTypes());
        System.out.println("============c=="+event.getClassName());
        System.out.println("============c=="+event.getBeforeText());


    }

    @Override
    public void onInterrupt() {

    }
}
