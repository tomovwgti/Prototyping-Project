
package com.tomovwgti.weather;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tomovwgti.weather.HttpLoader.HttpListener;

public class WeatherReportActivity extends Activity implements HttpListener, LocationListener {
    private final static String TAG = WeatherReportActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final HttpLoader loader = new HttpLoader(this);

        Button btn = (Button) findViewById(R.id.get);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loader.execute();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void viewResult(String temp, String weather) {
        TextView tempText = (TextView) findViewById(R.id.temperature);
        TextView weatherText = (TextView) findViewById(R.id.weather);

        tempText.setText("気温 : " + temp + " ℃");
        weatherText.setText("天気 : " + weather);
    }

    @Override
    public void onLocationChanged(Location arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }
}
