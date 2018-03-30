package com.example.android.sunshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";
    private TextView tvDetailWeatherData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent =  getIntent();
        Bundle bundle  = intent.getExtras();
        tvDetailWeatherData =  (TextView) findViewById(R.id.tv_detail_weather_data);

        if (bundle != null){
            String weatherDetailInformation = (String) bundle.get(Intent.EXTRA_TEXT);
            tvDetailWeatherData.setText(weatherDetailInformation);
        }
        //  (2) Display the weather forecast that was passed from MainActivity
    }
}