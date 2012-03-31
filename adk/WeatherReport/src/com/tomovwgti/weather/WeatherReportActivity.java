
package com.tomovwgti.weather;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.tomovwgti.android.accessory.AccessoryBaseActivity;
import com.tomovwgti.weather.ImageLoader.ImageListener;
import com.tomovwgti.weather.PlaceLoader.PlaceListener;
import com.tomovwgti.weather.WeatherOnlineLoader.WeatherOnlineListener;

public class WeatherReportActivity extends AccessoryBaseActivity implements WeatherOnlineListener,
        PlaceListener, LocationListener {
    private final static String TAG = WeatherReportActivity.class.getSimpleName();

    private LocationManager mLocationManager;
    private WeatherOnlineLoader mWeatherLoader;
    private PlaceLoader mPlaceLoader;
    private ProgressDialog mProgress;

    @Override
    protected void showControls() {
        setContentView(R.layout.main);

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mWeatherLoader = new WeatherOnlineLoader(this);
        mPlaceLoader = new PlaceLoader(this);

        mProgress = new ProgressDialog(this);
        mProgress.setMessage("情報取得中...");
        mProgress.show();
    }

    @Override
    protected void onResumeActivity() {
        Log.i(TAG, "Weather:onResume");
        if (mLocationManager != null) {
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        }
    }

    @Override
    public void onPauseActivity() {
        Log.i(TAG, "Weather:onPause");
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(this);
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
    public void onLocationChanged(Location location) {
        mLocationManager.removeUpdates(this);
        if (mLocationManager == null) {
            // 位置取得キャンセル
            return;
        }
        mLocationManager = null;

        String lat = String.valueOf(location.getLatitude());
        String lon = String.valueOf(location.getLongitude());

        // 天気情報
        mWeatherLoader.execute(lat, lon);
        // 位置情報取得
        mPlaceLoader.execute(lat, lon);

        Log.v("----------", "----------");
        Log.v("Latitude", String.valueOf(location.getLatitude()));
        Log.v("Longitude", String.valueOf(location.getLongitude()));
        Log.v("Accuracy", String.valueOf(location.getAccuracy()));
        Log.v("Altitude", String.valueOf(location.getAltitude()));
        Log.v("Time", String.valueOf(location.getTime()));
        Log.v("Speed", String.valueOf(location.getSpeed()));
        Log.v("Bearing", String.valueOf(location.getBearing()));
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}
