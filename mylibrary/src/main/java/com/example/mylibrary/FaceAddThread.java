package com.example.mylibrary;

import org.json.JSONException;

/**
 * Created by Administrator on 2017/9/13.
 */

public class FaceAddThread extends Thread {
    private String  netValue=null;
   private byte[]  bytes;
    public FaceAddThread(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public void run() {
      /*  try {
          //  netValue= Main2Activity.faceDetectString(bytes);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }
    public  String  getNetString(){
              start();
        while (isAlive()){
        }
        return netValue;
    }

}
