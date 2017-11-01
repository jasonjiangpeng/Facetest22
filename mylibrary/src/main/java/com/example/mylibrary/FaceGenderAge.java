package com.example.mylibrary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.FaceDetector;

import com.megvii.cloud.http.CommonOperate;
import com.megvii.cloud.http.Response;

import org.json.JSONException;

/**
 * Created by Administrator on 2017/10/16.
 */

public class FaceGenderAge {
    public final   static String key = "aRKh7sDhT536YLHORdXOFFBqJOeF5Gzv";//api_key
    public final static String secret = "zrfIe28avDUP4z36mEy9vg2csDXszxB8";
      public static boolean isHasFace(byte[] bytes){
        if (bytes==null){
            return false;
        }
        Bitmap bitmap =bytesToBimap(bytes);
          Bitmap bitmap1=bitmap.copy(Bitmap.Config.RGB_565,true);
        FaceDetector.Face[] faces=new FaceDetector.Face[Constants.Detection];
        FaceDetector detector=new FaceDetector(bitmap1.getWidth(),bitmap1.getHeight(),Constants.Detection);
        int faces1 = detector.findFaces(bitmap1, faces);
          System.out.println(faces1+"=================");
        return faces1>0?true:false;
    }
    public static boolean isHasFace(Bitmap bitmap){
        if (bitmap==null){
            return false;
        }

        FaceDetector.Face[] faces=new FaceDetector.Face[Constants.Detection];
        FaceDetector detector=new FaceDetector(bitmap.getWidth(),bitmap.getHeight(),Constants.Detection);
        int faces1 = detector.findFaces(bitmap, faces);
        return faces1>0?true:false;
    }
    /*转换成图片*/
    private static Bitmap bytesToBimap(byte[] b) {
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
    private  static String getFaceTokenString(Response response) throws JSONException {
        if(response.getStatus() != 200){
            return "Net Error";
        }
        return new String(response.getContent());
    }
    private  static boolean getFaceToken(Response response) throws JSONException {
        if(response.getStatus() != 200){
            return false;
        }
        return true;
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
}
