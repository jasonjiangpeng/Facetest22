package com.example.mylibrary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.megvii.cloud.http.CommonOperate;
import com.megvii.cloud.http.FaceSetOperate;
import com.megvii.cloud.http.Response;

import java.io.ByteArrayOutputStream;

/**
 * Created by Administrator on 2017/10/17.
 */

public class FaceDetection {
    /*创建Face储存*/
    public void createFace(FaceSetOperate faceSet){
        Response faceset = null;
        try {
            faceset = faceSet.createFaceSet(null,"testst",null,null,null, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String faceSetResult = new String(faceset.getContent());
    }
    /*添加FaceToken*/
    public boolean addFace(FaceSetOperate faceSet,String faceToken){
        try {
            Response response = faceSet.addFaceByOuterId(faceToken, "zchtFace");
            if (response.getStatus()!=200){
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    /*检测Face*/
    public void detectionFace(byte[] bytes){
        CommonOperate commonOperate = new CommonOperate(FaceGenderAge.key, FaceGenderAge.secret, false);
        try {
            Response  response=commonOperate.searchByOuterId(null,null,bytes,"zchtFace",1);
            System.out.println(new String(response.getContent()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
