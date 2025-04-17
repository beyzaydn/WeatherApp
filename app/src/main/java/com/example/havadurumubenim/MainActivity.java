package com.example.havadurumubenim;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    EditText et;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.textView);
        et = findViewById(R.id.editTextText);
        iv = findViewById(R.id.imageView);
    }

    public void Getir(View v) {
        HavaDurumuGetir hdg = new HavaDurumuGetir();

        JSONObject obj = null;
        try {
            if (et.getText().toString().equals(""))
                obj = hdg.execute().get();
            else
                obj = hdg.execute(et.getText().toString()).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        Log.d("HAVA DURUMU", obj.toString());

        try {
            JSONObject main = obj.getJSONObject("main");
            double temp = main.getDouble("temp") - 275.13;
            tv.setText(String.format("%.2f C", temp));

            // Hava durumunu al
            JSONObject weather = obj.getJSONArray("weather").getJSONObject(0);
            String iconCode = weather.getString("icon");

            // Icon URL'sini oluştur
            String iconUrl = "https://openweathermap.org/img/wn/" + iconCode + "@2x.png";

            // Resmi yükle ve görüntüle
            new ImageLoadTask(iconUrl, iv).execute();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
