package com.example.mylibrary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.FaceDetector;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.megvii.cloud.http.CommonOperate;
import com.megvii.cloud.http.Response;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class Main2Activity extends UnityPlayerActivity {
/*    private final   static String key = "aRKh7sDhT536YLHORdXOFFBqJOeF5Gzv";//api_key
    private final static String secret = "zrfIe28avDUP4z36mEy9vg2csDXszxB8";
    private final static int  Count = 6;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void isHasFace(byte[] bytes,int pohotW,int pohotH){
     final    Bitmap  bitmap=Utils.rgbToBitmap(bytes,pohotW,pohotH);
        if (FaceGenderAge.isHasFace(bitmap)){
            Utils.sendUnityMessage("已检测到人脸，正在检测年龄性别");
        }else {
            Utils.sendUnityMessage("没有检测到人脸,请检查设备");
        }
    }
  public void  isHasFaceDemo(byte[] bytes){
      if (FaceGenderAge.isHasFace(bytes)){
          try {
              String  detectData=FaceGenderAge.faceDetectString(bytes);
              Utils.sendUnityMessage(detectData);
             // UnityPlayer.UnitySendMessage("Main Camera","message",detectData);
          } catch (JSONException e) {
              e.printStackTrace();
          }
      }else {
          Utils.sendUnityMessage("未检测到人脸");
      }
  }

 private  Handler  handler =new Handler(Looper.getMainLooper()){
     @Override
     public void handleMessage(Message msg) {
         handler.postDelayed(new Runnable() {
             @Override
             public void run() {

              //   Utils.sendUnityMessage(detectData);
             }
         },2000);
     }
 };
/* public void startFace(byte[] bytes){
     String  detectData= "没有数据";
     try {
         detectData = FaceGenderAge.faceDetectString(bytes);
         Utils.sendUnityMessage(detectData);
     } catch (JSONException e) {
         e.printStackTrace();
     }

 }*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
