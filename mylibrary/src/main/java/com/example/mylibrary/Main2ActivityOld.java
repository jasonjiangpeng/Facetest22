package com.example.mylibrary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.FaceDetector;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.megvii.cloud.http.CommonOperate;
import com.megvii.cloud.http.Response;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class Main2ActivityOld extends UnityPlayerActivity {
    private  String netValue=null;
    String[] parameterName=null;
    private Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            UnityPlayer.UnitySendMessage("Main Camera","message",netValue);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void getUnity3DParameter(String u3DValue){
         if (u3DValue==null||u3DValue.length()<1){
            return;
         }
        parameterName = u3DValue.split(",");
    }
  public void  isHasFaceDemo(byte[] bytes){
      if (hasFace(bytes)){
          UnityPlayer.UnitySendMessage("Main Camera","message","有");
      }else {
          UnityPlayer.UnitySendMessage("Main Camera","message","没有");
      }
  }
  public void  requestStringDemo(byte[] bytes){
      try {
          String  value=faceDetectString(bytes);
          UnityPlayer.UnitySendMessage("Main Camera","message",value);
      } catch (JSONException e) {
          e.printStackTrace();
      }
  }

    public boolean hasFace(byte[] bytes){
        return new FaceThread(bytes).isHasFace();
    }
    public String getBytesSum(byte[] bytes){
        String a="";
        for (int i = 0; i <bytes.length ; i++) {
            a+=bytes[i];
        }
        return a;
    }
    public static boolean isTure(int b) {
         return b>2;
    }
    public static String getName(String b) {
     return "Jason:"+b;
    }
    public static String getName() {
        return "Jason:";
    }
    private final   static String key = "aRKh7sDhT536YLHORdXOFFBqJOeF5Gzv";//api_key
    private final static String secret = "zrfIe28avDUP4z36mEy9vg2csDXszxB8";
    private final static int  Count = 6;
    /*byte转bitmap*/
    public static Bitmap bytesToBimap(byte[] b) {
        if (b.length > 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length,getBitmapOptions());
        } else {
            return null;
        }
    }
    /*RGB-565 image*/
    private static BitmapFactory.Options getBitmapOptions() {
        BitmapFactory.Options bitmapOption = new BitmapFactory.Options();
        bitmapOption.inPreferredConfig = Bitmap.Config.RGB_565;
        return bitmapOption;
    }
    public static byte[] bitmapToBytes(Bitmap b) {
        if (b==null){
            return null;
        }
        ByteArrayOutputStream  c=new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG,100,c);
        return c.toByteArray();
    }
    public static boolean isHasFace(Bitmap bitmap,int count){
        if (bitmap==null||count<1){
            return false;
        }
        FaceDetector.Face[] faces=new FaceDetector.Face[count];
        FaceDetector detector=new FaceDetector(bitmap.getWidth(),bitmap.getHeight(),count);
        int faces1 = detector.findFaces(bitmap, faces);
        return faces1>0?true:false;
    }

    public static boolean isHasFace(Bitmap bitmap){
        if (bitmap==null){
            return false;
        }
        FaceDetector.Face[] faces=new FaceDetector.Face[Count];
        FaceDetector detector=new FaceDetector(bitmap.getWidth(),bitmap.getHeight(),Count);
        int faces1 = detector.findFaces(bitmap, faces);
        System.out.println(faces1+"==========");
        return faces1>0?true:false;
    }
    /*判断是否有人脸*/
    public static boolean isHasFace(byte[] bytes){
        if (bytes==null){
            return false;
        }
        Bitmap bitmap =bytesToBimap(bytes);
        FaceDetector.Face[] faces=new FaceDetector.Face[Count];
        FaceDetector detector=new FaceDetector(bitmap.getWidth(),bitmap.getHeight(),Count);
        int faces1 = detector.findFaces(bitmap, faces);
        return faces1>0?true:false;
    }
    /*is there a human face*/
    public static boolean isHasFace(byte[] bytes,int count){
        Bitmap  bitmap =bytesToBimap(bytes);
        if (bitmap==null||count<1){
            return false;
        }
        FaceDetector.Face[] faces=new FaceDetector.Face[count];
        FaceDetector detector=new FaceDetector(bitmap.getWidth(),bitmap.getHeight(),count);
        int faces1 = detector.findFaces(bitmap, faces);
        return faces1>0?true:false;
    }
    /*face Detect counts*/
    public static  int[] faceDetect(byte[] b){
        CommonOperate commonOperate = new CommonOperate(key, secret, false);
        try {
            Response response1 = commonOperate.detectByte(b, 0, "gender,age");
            if (getFaceToken(response1)){
                return faceJsonParse(new String(response1.getContent()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public  void   requestNetValues(final byte[] bytes){
          handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    netValue=faceDetectString(bytes);
                    if (netValue!=null){
                        handler.sendEmptyMessage(0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public String  getNetValue(){
        return netValue;
    }
    public static  String faceDetectString(byte[] b) throws JSONException {
        CommonOperate commonOperate = new CommonOperate(key, secret, false);
        Response response1 = null;
        try {
            response1 = commonOperate.detectByte(b, 0, "gender,age");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getFaceTokenString(response1);
    }
    /*net response is normal?*/
    private  static boolean getFaceToken(Response response) throws JSONException {
        if(response.getStatus() != 200){
            return false;
        }
        return true;
    }
    private  static String getFaceTokenString(Response response) throws JSONException {
        if(response.getStatus() != 200){
            return "Error";
        }
        return new String(response.getContent());
    }
    /* parse data*/
    private static int[] faceJsonParse(String value) {
        JSONObject jsonObject = null;
        int[] ages=null;
        try {
            jsonObject = new JSONObject(value);
            JSONArray faces = jsonObject.getJSONArray("faces");
            ages=new int[faces.length()];
            for (int i = 0; i < faces.length(); i++) {
                ages[i]=faces.getJSONObject(i).getJSONObject("attributes").getJSONObject("age").getInt("value");
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return ages;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        for (int i = 0; i <parameterName.length ; i++) {
            parameterName[i]=null;
        }
    }
}
