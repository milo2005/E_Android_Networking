package com.universidadcauca.movil.clima.net;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;

/**
 * Created by DarioFernando on 14/05/2015.
 */
public class HttpThread extends Thread {

    public interface HttpThreadI{
        public Handler getHandler();
    }

    public static final int POST = 0;
    public static final int GET =1;

    String url;
    String data;
    int type;

    HttpConnection httpCon;

    HttpThreadI httpI;

    public HttpThread(HttpThreadI httpI,String url, String data, int type) {

        this.url = url;
        this.data = data;
        this.type = type;
        this.httpI = httpI;

        httpCon = new HttpConnection();
    }

    @Override
    public void run() {
        String rta = null;
        try {
            if(type == POST)
                rta = httpCon.requestByPost(url,data);
            else
                rta = httpCon.requestStringByGet(url, data);
        } catch (IOException e) {
            rta=null;
            e.printStackTrace();
        }

        Message msg = httpI.getHandler().obtainMessage();
        msg.what = type;
        msg.obj = rta;

        httpI.getHandler().sendMessage(msg);

    }
}
