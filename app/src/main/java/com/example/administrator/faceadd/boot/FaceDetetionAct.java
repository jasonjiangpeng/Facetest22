package com.example.administrator.faceadd.boot;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.faceadd.R;
import com.megvii.cloud.http.CommonOperate;
import com.megvii.cloud.http.FaceSetOperate;
import com.megvii.cloud.http.HttpRequest;
import com.megvii.cloud.http.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/10/16.
 */

public class FaceDetetionAct extends Activity {
    private final   static String key = "aRKh7sDhT536YLHORdXOFFBqJOeF5Gzv";//api_key
    private final static String secret = "zrfIe28avDUP4z36mEy9vg2csDXszxB8";
    String imageUrl = "http://pic1.hebei.com.cn/003/005/869/00300586905_449eedbb.jpg";
    StringBuffer sb = new StringBuffer();
    FaceSetOperate FaceSet;
    TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView= (TextView) findViewById(R.id.tv1);
        FaceSet = new FaceSetOperate(key, secret, false);
        if(TextUtils.isEmpty(key) || TextUtils.isEmpty(secret)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("please enter key and secret");
            builder.setTitle("");
            builder.show();
        }else {
            new Thread(){
                @Override
                public void run() {
                  /*  ArrayList<String> faces = new ArrayList<>();
                    String faceTokens = creatFaceTokens(faces);
                    Response faceset = null;
                    try {
                        faceset = FaceSet.createFaceSet(null,"testst",null,faceTokens,null, 1);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String faceSetResult = new String(faceset.getContent());
                    Log.e("faceSetResult",faceSetResult);
                    if(faceset.getStatus() == 200){
                        sb.append("\n");
                        sb.append("\n");
                        sb.append("faceSet creat success");
                        sb.append("\n");
                        sb.append("create result: ");
                        sb.append(faceSetResult);
                    }else{
                        sb.append("\n");
                        sb.append("\n");
                        sb.append("faceSet creat faile");
                        sb.append("\n");
                        sb.append("create result: ");
                        sb.append(faceSetResult);
                    }
                    System.out.println();*/


                }
            }.start();
        }
    }
    private byte[] getBitmap(int res){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), res);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

        return baos.toByteArray();
    }
    private String getFaceToken(Response response) throws JSONException {
        if(response.getStatus() != 200){
            return new String(response.getContent());
        }
        String res = new String(response.getContent());
        Log.e("response", res);
        JSONObject json = new JSONObject(res);
        String faceToken = json.optJSONArray("faces").optJSONObject(0).optString("face_token");
        return faceToken;
    }
    private String creatFaceTokens(ArrayList<String> faceTokens){
        if(faceTokens == null || faceTokens.size() == 0){
            return "";
        }
        StringBuffer face = new StringBuffer();
        for (int i = 0; i < faceTokens.size(); i++){
            if(i == 0){
                face.append(faceTokens.get(i));
            }else{
                face.append(",");
                face.append(faceTokens.get(i));
            }
        }
        return face.toString();
    }
    public void testOnclick(View view){
        new Thread(){
            @Override
            public void run() {
             /*   CommonOperate commonOperate = new CommonOperate(key, secret, false);
                Response response1 = null;
                try {
                    response1 = commonOperate.detectByte(getBitmap(R.drawable.timg), 0, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String faceToken1 = null;

                try {
                    faceToken1 = getFaceToken(response1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sb.append("faceToken1: ");
                sb.append(faceToken1);
                System.out.println(faceToken1);
                try {
                    Response response = FaceSet.addFaceByOuterId(faceToken1, "testst");
                    System.out.println(new String(response.getContent()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(sb.toString());
                    }
                });*/
             deteFace();
            }
        }.start();

   /*     new Thread(){
            @Override
            public void run() {
                CommonOperate commonOperate = new CommonOperate(key, secret, false);
                Response res = null;
                try {
                    res = commonOperate.searchByFaceSetToken(null, imageUrl, null, "test", 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String result = new String(res.getContent());
                Log.e("result", result);
                sb.append("\n");
                sb.append("\n");
                sb.append("search result: ");
                sb.append(result);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(sb.toString());
                    }
                });
            }
        }.start();*/

    }
   public void deteFace(){
       CommonOperate commonOperate = new CommonOperate(key, secret, false);
       try {
           Response  response=commonOperate.searchByOuterId(null,null,getBitmap(R.drawable.timg),"testst",1);
           System.out.println(new String(response.getContent()));
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
   public boolean addFace(FaceSetOperate faceSet,String faceToken){
       try {
           Response response = faceSet.addFaceByOuterId(faceToken, "testst");
           if (response.getStatus()!=200){
               return false;
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return true;
   }
   public void createFace(FaceSetOperate faceSet){
       Response faceset = null;
       try {
           faceset = faceSet.createFaceSet(null,"testst",null,null,null, 1);
       } catch (Exception e) {
           e.printStackTrace();
       }
       String faceSetResult = new String(faceset.getContent());
   }
}
