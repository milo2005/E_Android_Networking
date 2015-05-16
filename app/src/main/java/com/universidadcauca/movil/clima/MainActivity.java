package com.universidadcauca.movil.clima;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.universidadcauca.movil.clima.net.HttpTask;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity implements HttpTask.HttpTaskI {

    String url ="https://query.yahooapis.com/v1/public/yql";
    String urlData="q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20%28select%20woeid%20from%20geo.places%281%29%20where%20text%3D%22popayan%2C%20co%22%29&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";


    TextView pronostico, temperatura, presion, humedad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pronostico = (TextView) findViewById(R.id.txt_pronostico);
        temperatura = (TextView) findViewById(R.id.txt_temp);
        presion = (TextView) findViewById(R.id.txt_pres);
        humedad = (TextView) findViewById(R.id.txt_hum);

        HttpTask task = new HttpTask(this, url, HttpTask.GET);
        task.execute(urlData);


    }


    private void processWeather(String rta) {
        try {
            JSONObject obj = new JSONObject(rta);
            JSONObject q = obj.getJSONObject("query");
            JSONObject r = q.getJSONObject("results");
            JSONObject ch = r.getJSONObject("channel");
            JSONObject a = ch.getJSONObject("atmosphere");

            String h = a.getString("humidity");
            String p = a.getString("pressure");

            JSONObject i = ch.getJSONObject("item");
            JSONObject c = i.getJSONObject("condition");

            String pr = c.getString("text");
            String t = c.getString("temp");


            humedad.setText(h);
            presion.setText(p);
            pronostico.setText(pr);
            temperatura.setText(t);



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setResult(String rta) {
        processWeather(rta);
    }
}
