
package com.tomovwgti.weather;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.tomovwgti.android.accessory.AccessoryBaseActivity;
import com.tomovwgti.weather.ImageLoader.ImageListener;
import com.tomovwgti.weather.PlaceLoader.PlaceListener;
import com.tomovwgti.weather.WeatherOnlineLoader.WeatherOnlineListener;

public class WeatherReportActivity extends AccessoryBaseActivity implements WeatherOnlineListener,
        PlaceListener, ConnectionCallbacks, OnConnectionFailedListener {
    final static String TAG = WeatherReportActivity.class.getSimpleName();

    private WeatherOnlineLoader mWeatherLoader;
    private PlaceLoader mPlaceLoader;
    private ProgressDialog mProgress;

    private LocationClient mLocationClient;

    @Override
    protected void showControls() {
        setContentView(R.layout.main);
        // check Google Play service APK is available and up to date.
        // see http://developer.android.com/google/play-services/setup.html
        final int result = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            Toast.makeText(this, "Google Play service is not available (status=" + result + ")",
                    Toast.LENGTH_LONG).show();
            finish();
        }

        mLocationClient = new LocationClient(this, this, this);
        mWeatherLoader = new WeatherOnlineLoader(this);
        mPlaceLoader = new PlaceLoader(this);

        mProgress = new ProgressDialog(this);
        mProgress.setMessage("情報取得中...");
        mProgress.setCancelable(false);
        mProgress.show();
    }

    @Override
    protected void onResumeActivity() {
        Log.i(TAG, "Weather:onResume");
        if (mLocationClient != null) {
            Log.i(TAG, "Weather:connect");
            mLocationClient.connect();
        }
    }

    @Override
    public void onPauseActivity() {
        Log.i(TAG, "Weather:onPause");
        if (mLocationClient != null) {
            mLocationClient.disconnect();
        }
        if (mProgress != null) {
            mProgress.dismiss();
        }
    }

    @Override
    public void viewResult(String temp, String weather, String imageUrl) {
        final TextView tempText = (TextView) findViewById(R.id.temperature);
        final TextView weatherText = (TextView) findViewById(R.id.weather);
        final ImageView weatherImage = (ImageView) findViewById(R.id.image);

        ImageLoader imageLoader = new ImageLoader(new ImageListener() {
            @Override
            public void viewResult(Bitmap image) {
                weatherImage.setImageBitmap(image);
                mProgress.dismiss();
            }
        });
        imageLoader.execute(imageUrl);

        tempText.setText("気温 : " + temp + " ℃");
        weatherText.setText("天気 : " + weather);

        LedLight led = new LedLight();
        // 気温によってLEDの色を変える
        int temperature = Integer.parseInt(temp);
        if (temperature > 25) {
            // ORANGE
            led.red = 255;
            led.green = 80;
            led.blue = 0;
        } else if (temperature > 20) {
            // GREEN
            led.red = 72;
            led.green = 225;
            led.blue = 0;
        } else if (temperature > 10) {
            // YELLOW
            led.red = 180;
            led.green = 180;
            led.blue = 50;
        } else if (temperature > 0) {
            // GLAY
            led.red = 40;
            led.green = 40;
            led.blue = 260;
        } else {
            // GLAY
            led.red = 40;
            led.green = 40;
            led.blue = 40;
        }
        led.sendData();
    }

    @Override
    public void viewResult(String place) {
        TextView placeText = (TextView) findViewById(R.id.place);
        placeText.setText("場所 : " + place);
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();

        Location loc = mLocationClient.getLastLocation();
        Log.d("XXX", "location=" + loc.toString());

        String lat = String.valueOf(loc.getLatitude());
        String lon = String.valueOf(loc.getLongitude());

        // 天気情報
        mWeatherLoader.execute(lat, lon);
        // 位置情報取得
        mPlaceLoader.execute(lat, lon);
    }

    @Override
    public void onDisconnected() {
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
    }
}
