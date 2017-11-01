package com.example.administrator.faceadd.boot;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLException;

/**
 * Created by Administrator on 2017/10/17.
 */

public class HttpRequest {
    private static final int CONNECT_TIME_OUT = 30000;
    private static final int READ_OUT_TIME = 50000;
    private static String boundaryString = getBoundary();

    public HttpRequest() {
    }

    protected static Response post(String url, HashMap<String, String> map, HashMap<String, byte[]> fileMap) throws Exception {
        URL url1 = new URL(url);
        HttpsURLConnection conne = (HttpsURLConnection)url1.openConnection();
        conne.setDoOutput(true);
        conne.setUseCaches(false);
        conne.setRequestMethod("POST");
        conne.setConnectTimeout(30000);
        conne.setReadTimeout('썐');
        conne.setRequestProperty("accept", "*/*");
        conne.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundaryString);
        conne.setRequestProperty("connection", "Keep-Alive");
        conne.setRequestProperty("user-agent", "Mozilla/4.0 (compatible;MSIE 6.0;Windows NT 5.1;SV1)");
        DataOutputStream obos = new DataOutputStream(conne.getOutputStream());
        Iterator iter = map.entrySet().iterator();

        Map.Entry ins;
        while(iter.hasNext()) {
            ins = (Map.Entry)iter.next();
            String code = (String)ins.getKey();
            String baos = (String)ins.getValue();
            obos.writeBytes("--" + boundaryString + "\r\n");
            obos.writeBytes("Content-Disposition: form-data; name=\"" + code + "\"\r\n");
            obos.writeBytes("\r\n");
            obos.writeBytes(baos + "\r\n");
        }

        if(fileMap != null && fileMap.size() > 0) {
            Iterator ins1 = fileMap.entrySet().iterator();

            while(ins1.hasNext()) {
                Map.Entry code1 = (Map.Entry)ins1.next();
                obos.writeBytes("--" + boundaryString + "\r\n");
                obos.writeBytes("Content-Disposition: form-data; name=\"" + (String)code1.getKey() + "\"; filename=\"" + encode(" ") + "\"\r\n");
                obos.writeBytes("\r\n");
                obos.write((byte[])code1.getValue());
                obos.writeBytes("\r\n");
            }
        }

        obos.writeBytes("--" + boundaryString + "--\r\n");
        obos.writeBytes("\r\n");
        obos.flush();
        obos.close();
        ins = null;
        int code2 = conne.getResponseCode();

        InputStream ins2;
        try {
            if(code2 == 200) {
                ins2 = conne.getInputStream();
            } else {
                ins2 = conne.getErrorStream();
            }
        } catch (SSLException var14) {
            var14.printStackTrace();
            return new Response();
        }

        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
        byte[] buff = new byte[4096];

        int len;
        while((len = ins2.read(buff)) != -1) {
            baos1.write(buff, 0, len);
        }

        byte[] bytes = baos1.toByteArray();
        Response response = new Response(bytes, code2);
        ins2.close();
        return response;
    }

    private static String getBoundary() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for(int i = 0; i < 32; ++i) {
            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-".charAt(random.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_".length())));
        }

        return sb.toString();
    }

    private static String encode(String value) throws Exception {
        return URLEncoder.encode(value, "UTF-8");
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
