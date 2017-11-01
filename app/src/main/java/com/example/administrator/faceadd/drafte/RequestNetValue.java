/*
package com.example.administrator.faceadd.drafte;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.faceadd.R;
import com.example.mylibrary.FaceAddUtils;
import com.example.mylibrary.FaceThread;
import com.example.mylibrary.Main2Activity;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;

public class RequestNetValue extends AppCompatActivity {
    byte[] bytes={1,2,3};
    Bitmap bitmap;
    ImageView img;
    TextView tv1;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img= (ImageView) findViewById(R.id.img);
        tv1= (TextView) findViewById(R.id.tv1);
    }
    int a=0;
    boolean  isRun=true;
    int[] photovalue={R.drawable.timg,R.drawable.timg1,R.drawable.hint};
    public void testOnclick(View view){
        if (a>=photovalue.length){
            a=0;
        }
        if (isRun){
            isRun=false;
            bitmap= getBitmap(photovalue[a]);
            img.setImageBitmap(bitmap);
            ByteArrayOutputStream c=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,c);
            bytes=FaceAddUtils.bitmapToBytes(bitmap);
            a++;
            requestValue();
        }

       }
    public void requestValue(){
        new Thread() {
            @Override
            public void run() {
                try {
                    final String v = Main2Activity.faceDetectString(bytes);
                    isRun=true;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv1.setText(v);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private Bitmap getBitmap(int resid) {
        BitmapFactory.Options bitmapOption = new BitmapFactory.Options();
        bitmapOption.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), resid, bitmapOption);
        return myBitmap;
    }
    public void Object(Object o){
        int[] s= (int[]) o;
    }
}
*/
