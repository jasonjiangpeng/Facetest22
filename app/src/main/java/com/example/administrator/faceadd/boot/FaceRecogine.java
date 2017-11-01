package com.example.administrator.faceadd.boot;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.faceadd.R;


import java.io.ByteArrayOutputStream;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/10/17.
 */

public class FaceRecogine extends Activity {
    private final   static String key = "aRKh7sDhT536YLHORdXOFFBqJOeF5Gzv";//api_key
    private final static String secret = "zrfIe28avDUP4z36mEy9vg2csDXszxB8";
    private String  urlPath="https://api-cn.faceplusplus.com/imagepp/v1/recognizetext";
    ImageView img;
    TextView tv1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img= (ImageView) findViewById(R.id.img);
        tv1= (TextView) findViewById(R.id.tv1);
        final Bitmap  bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.jason);
        img.setImageBitmap(bitmap);
        new Thread(){
            @Override
            public void run() {
                try {
                    final Response response = searchByOuterId(bitmapToBytes(bitmap));
                    System.out.println(response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            tv1.setText(new String(response.getContent()));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }
    public static byte[] bitmapToBytes(Bitmap b) {
        if (b==null){
            return null;
        }
        ByteArrayOutputStream c=new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG,100,c);
        return c.toByteArray();
    }
    public Response searchByOuterId(byte[] buff) throws Exception {
        String url = this.urlPath;
        HashMap map = new HashMap();
        HashMap fileMap = new HashMap();
        map.put("api_key", this.key);
        map.put("api_secret", this.secret);

        if(buff != null) {
            fileMap.put("image_file", buff);
        }
        return HttpRequest.post(url, map, fileMap);
    }
}
