package com.chenyufengweb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.RectF;
import android.media.FaceDetector;

import com.megvii.cloud.http.CommonOperate;
import com.megvii.cloud.http.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/11.
 */

public class FaceAddUtils {
  private final   static String key = "aRKh7sDhT536YLHORdXOFFBqJOeF5Gzv";//api_key
    private final static String secret = "zrfIe28avDUP4z36mEy9vg2csDXszxB8";
    /*byte转bitmap*/
    public static Bitmap BytesToBimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
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
        FaceDetector.Face[] faces=new FaceDetector.Face[count];
        FaceDetector detector=new FaceDetector(bitmap.getWidth(),bitmap.getHeight(),count);
        int faces1 = detector.findFaces(bitmap, faces);
        return faces1>0?true:false;
    }
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
    private  static boolean getFaceToken(Response response) throws JSONException {
        if(response.getStatus() != 200){
            return false;
        }
        return true;
    }
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


}
