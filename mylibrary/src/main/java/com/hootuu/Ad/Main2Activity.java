package com.hootuu.Ad;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.example.mylibrary.FaceGenderAge;
import com.example.mylibrary.Utils;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

import org.json.JSONException;
import org.opencv.PedestrainDetecTion;
import org.opencv.android.OpenCVLoader;

public class Main2Activity extends UnityPlayerActivity {
/*    private final   static String key = "aRKh7sDhT536YLHORdXOFFBqJOeF5Gzv";//api_key
    private final static String secret = "zrfIe28avDUP4z36mEy9vg2csDXszxB8";
    private final static int  Count = 6;*/
private final String tag=getClass().getSimpleName();
       static {
    Utils.initNative();
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

  public void  isHasFaceDemo(byte[] bytes){
      if (FaceGenderAge.isHasFace(bytes)){
          sendUnityMessage("goto check");
          try {
              String  detectData=FaceGenderAge.faceDetectString(bytes);
              sendUnityMessage(detectData);
             // UnityPlayer.UnitySendMessage("Main Camera","message",detectData);
          } catch (JSONException e) {
              e.printStackTrace();
          }
      }else {
           sendUnityMessage("no check");
      }
  }
  public void  isLoadSoStatus(){
      if (OpenCVLoader.initDebug()){
          sendUnityMessage("加载SO库成功");
      }else {
          sendUnityMessage("加载SO库失败");
      }
  }
    /*赵迪用的*/
    public void  faceDetectionAdd(byte[] bytes){
        byte[] rotateBytes = Utils.rotateBitmap(bytes);
        String  detectData= null;
        try {
            detectData = FaceGenderAge.faceDetectString(rotateBytes);
            sendUnityMessage(detectData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
               // UnityPlayer.UnitySendMessage("Main Camera","message",detectData);
    }
    public void  faceDetectionAdd2(byte[] bytes){
     //   byte[] rotateBytes = Utils.rotateBitmap(bytes);
        String  detectData= null;
        try {
            detectData = FaceGenderAge.faceDetectString(bytes);
            sendUnityMessage(detectData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // UnityPlayer.UnitySendMessage("Main Camera","message",detectData);
    }
   public void sendUnityMessage(String data){
     //  UnityPlayer.UnitySendMessage("Main Camera","message","未检测到人脸");
       UnityPlayer.UnitySendMessage("FaceDetect","message",data);
   }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void isHasFace(byte[] bytes,int pohotW,int pohotH){
        final Bitmap bitmap=Utils.rgbToBitmap(bytes,pohotW,pohotH);
        if (FaceGenderAge.isHasFace(bitmap)){
            Utils.sendUnityMessage("detection age and geder");
              try {
                byte[] bytes1 = Utils.bitmapToBytes(bitmap);
                String  detectData=FaceGenderAge.faceDetectString(bytes1);
                  Utils.sendUnityMessage(detectData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            Utils.sendUnityMessage("No people");
        }
    }
    public void pedetrainDetection(String fileName){
        PedestrainDetecTion pedestrainDetecTion =new PedestrainDetecTion();
        String s=String.valueOf(pedestrainDetecTion.pedestrainDetection(fileName));
        Utils.sendUnityMessage(s);
    }
    public void pedetrainDetection2( byte[] fileBytes){
        Log.e(tag,"startDetection");
        PedestrainDetecTion pedestrainDetecTion =new PedestrainDetecTion();
        int c=pedestrainDetecTion.pedestrainDetection2(fileBytes);
        String s=String.valueOf(c);
        Log.e(tag,"endDetection");
        Utils.sendUnityMessage(s);


    }
    public void isHasFaceGo(byte[] bytes,int pohotW,int pohotH){
 /*       final Bitmap bitmap=Utils.rgbToBitmap(bytes,pohotW,pohotH);
        byte[] bytes1 = Utils.bitmapToBytes(bitmap);*/
        try {
            String  detectData=FaceGenderAge.faceDetectString(bytes);
            Utils.sendUnityMessage(detectData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
