package com.selcukcihan.android.tahlil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new HttpStuffTask().execute("http://labim.ihs.gov.tr/labim/HastaTetkikSonucSorgulama.aspx", "http://labim.ihs.gov.tr/labim/HastaTetkikSonucYazdir.aspx");
    }
}
