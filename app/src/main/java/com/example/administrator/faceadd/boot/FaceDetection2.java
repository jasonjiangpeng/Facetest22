package com.example.administrator.faceadd.boot;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.administrator.faceadd.R;
import com.example.mylibrary.FaceGenderAge;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;

/**
 * Created by Administrator on 2017/10/27.
 */

public class FaceDetection2 extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.timg2);
        ByteArrayOutputStream  byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        final byte[] bytes=byteArrayOutputStream.toByteArray();
        new Thread(){

            @Override
            public void run() {
                try {
                    String hasFace = FaceGenderAge.faceDetectString(bytes);
                    System.out.println(hasFace);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
