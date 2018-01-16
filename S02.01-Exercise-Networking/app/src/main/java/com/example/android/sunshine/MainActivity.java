/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.sunshine;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.sunshine.data.SunshinePreferences;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.NetPermission;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView mWeatherTextView;

    private EditText mLocationEditTextBox;

    private TextView mLocationsDisplayTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        /*
         * Using findViewById, we get a reference to our TextView from xml. This allows us to
         * do things like set the text of the TextView.
         */
        mWeatherTextView = (TextView) findViewById(R.id.tv_weather_data);

        mLocationEditTextBox = (EditText) findViewById(R.id.et_location_box);

        mLocationsDisplayTextView = (TextView) findViewById(R.id.tv_location_display);

        //  (4) Delete the dummy weather data. You will be getting REAL data from the Internet in this lesson.
        /*
         * This String array contains dummy weather data. Later in the course, we're going to get
         * real weather data. For now, we want to get something on the screen as quickly as
         * possible, so we'll display this dummy data.
         */
        String[] dummyWeatherData = null;


        //  (9) Call loadWeatherData to perform the network request to get the weather
    }


    //  (8) Create a method that will get the user's preferred location and execute your new AsyncTask and call it loadWeatherData
    private void loadWeatherData() {
        executeWeatherTask(false);
    }

    private void loadDefaultWeatherData() {
        mLocationEditTextBox.setText("");
        executeWeatherTask(true);
    }

    private void executeWeatherTask(boolean isDefault){

        if(isDefault || (mLocationEditTextBox != null && !mLocationEditTextBox.equals(""))){
            String location = isDefault ? SunshinePreferences.getPreferredWeatherLocation(this)
                                        : mLocationEditTextBox.getText().toString() ;

            mLocationsDisplayTextView.setText("Location : " + location);
            new WeatherQueryTask().execute(location);
        }else{
            Log.d("main actiity","see line 76 main activity");
        }
    }

    public class  WeatherQueryTask extends AsyncTask<String, Void, String[]> {
        @Override
        protected String[] doInBackground(String... params) {
            if (params.length == 0)
                return null;

            URL weatherURL = NetworkUtils.buildUrl(params[0]);
            String[] simpleJsonWeatherData = null;

            try{
                String jsonWeatherHttpResponse = NetworkUtils.getResponseFromHttpUrl(weatherURL);

                simpleJsonWeatherData = OpenWeatherJsonUtils.getSimpleWeatherStringsFromJson(MainActivity.this,
                        jsonWeatherHttpResponse);

            }catch (Exception e) {
                e.printStackTrace();
            }

            return simpleJsonWeatherData;
        }

        @Override
        protected void onPostExecute(String[] weatherData) {
            for (String weatherString : weatherData){
                mWeatherTextView.append(weatherString + "\n\n");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasSelected =  item.getItemId();

        switch (itemThatWasSelected){
            case R.id.action_search:
                loadWeatherData();
                break;
            case R.id.action_default:
                loadDefaultWeatherData();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //  (5) Create a class that extends AsyncTask to perform network requests
    //  (6) Override the doInBackground method to perform your network requests
    //  (7) Override the onPostExecute method to display the results of the network request
}