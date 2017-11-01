package com.example.mylibrary;

/**
 * Created by Administrator on 2017/9/13.
 */

public class FaceThread extends Thread {
    private boolean  hasFace=false;
   private byte[]  bytes;
    public FaceThread(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public void run() {
             hasFace= FaceAddUtils.isHasFace(bytes);
    }
    public  boolean  isHasFace(){
        start();
        while (isAlive()){

        }

        return hasFace;
    }

}
