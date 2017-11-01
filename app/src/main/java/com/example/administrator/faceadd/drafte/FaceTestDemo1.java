package com.example.administrator.faceadd.drafte;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.faceadd.R;
import com.example.mylibrary.FaceGenderAge;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;

/**
 * Created by Administrator on 2017/10/19.
 */

public class FaceTestDemo1  extends Activity{
    TextView tv1;
    ImageView img;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1= (TextView) findViewById(R.id.tv1);
        img= (ImageView) findViewById(R.id.img);
         Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.tima);
      //  System.gc();
        final    Bitmap bitmap1=rotateBitmap(bitmap,180);
        new Thread(){
            @Override
            public void run() {
                testDemo(bitmapToBytes(bitmap1));
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
             //   isHasFaceDemo(bitmapToBytes(bitmap));
                if (FaceGenderAge.isHasFace(bitmapToBytes(bitmap1))){
                    System.out.println("=====================");
                }else {
                    System.out.println("==========xxxxxxxxxx===========");
                }
                String  detectData= null;
                try {
                    detectData = FaceGenderAge.faceDetectString(bitmapToBytes(bitmap1));
                    sendData(detectData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.start();
        img.setImageBitmap(bitmap1);


    }
    private Bitmap rotateBitmap(Bitmap origin, float alpha) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(alpha);
        // 围绕原地进行旋转
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        return newBM;
    }
    public  byte[] bitmapToBytes(Bitmap b) {
        if (b==null){
            return null;
        }
        ByteArrayOutputStream c=new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG,100,c);
        return c.toByteArray();
    }
    public    void  isHasFaceDemo(byte[] bytes){
        if (FaceGenderAge.isHasFace(bytes)){
            try {
                System.out.println("======================");
                String  detectData=FaceGenderAge.faceDetectString(bytes);
                sendData(detectData);
          //      sendUnityMessage(detectData);
                // UnityPlayer.UnitySendMessage("Main Camera","message",detectData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("===========xxxxxxxxxxxxx===========");
            //sendUnityMessage("未检测到人脸");
        }
    }
    private  Handler handler =new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            String data = msg.getData().getString("data");
            tv1.setText(data);
        }
    };
    public  void sendData(String value){
        Message message=new Message();
        Bundle bundle =new Bundle();
        bundle.putString("data",value);
        message.setData(bundle);
        handler.sendMessage(message);
    }
    public void testDemo(byte[] bytes){
        FaceGenderAge.isHasFace(bytes);
    }
}

