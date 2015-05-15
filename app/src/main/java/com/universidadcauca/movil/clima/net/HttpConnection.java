package com.universidadcauca.movil.clima.net;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

/**
 * Created by DarioFernando on 14/05/2015.
 */
public class HttpConnection {

    HttpClient client;

    public HttpConnection(){
        client = new DefaultHttpClient();
    }

    /**
     * Ejecuta un GET con una url y unos dato
     * @param url url de pagina
     * @param data datos con el formato key=value&key2=value2...
     * @return Respuesta String de la solicitud
     */
    public String requestStringByGet(String url, String data) throws IOException {
        HttpGet request = new HttpGet(url+"?"+data);
        HttpResponse response = client.execute(request);
        return processResponse(response);
    }

    /**
     * Ejecuta un POST con una url y unos dato
     * @param url url de pagina
     * @param data datos con el formato key=value&key2=value2...
     * @return Respuesta String de la solicitud
     */
    public String requestByPost(String url, String data) throws IOException {

        List<NameValuePair> dataForm =  new ArrayList<>();
        String[] formItems = data.split("&");

        for(int i=0;i<formItems.length;i++){
            String[] formItem = formItems[i].split("=");
            BasicNameValuePair vP = new BasicNameValuePair(formItem[0],formItem[1]);
            dataForm.add(vP);
        }

        UrlEncodedFormEntity form = new UrlEncodedFormEntity(dataForm);
        HttpPost request = new HttpPost(url);
        request.setEntity(form);

        HttpResponse response = client.execute(request);

        return processResponse(response);
    }

    public String processResponse(HttpResponse response) throws IOException {
        String rta= EntityUtils.toString(response.getEntity());
        return rta;
    }
}
