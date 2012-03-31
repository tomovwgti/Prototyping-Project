
package com.tomovwgti.weather;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.tomovwgti.weather.ImageLoader.ImageListener;
import com.tomovwgti.weather.PlaceLoader.PlaceListener;
import com.tomovwgti.weather.WeatherOnlineLoader.WeatherOnlineListener;

public class WeatherReportActivity extends Activity implements WeatherOnlineListener,
        PlaceListener, LocationListener {
    private final static String TAG = WeatherReportActivity.class.getSimpleName();

    private LocationManager mLocationManager;
    private WeatherOnlineLoader mWeatherLoader;
    private PlaceLoader mPlaceLoader;
    private ProgressDialog mProgress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mWeatherLoader = new WeatherOnlineLoader(this);
        mPlaceLoader = new PlaceLoader(this);

        mProgress = new ProgressDialog(this);
        mProgress.setMessage("情報取得中...");
        mProgress.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mLocationManager != null) {
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(this);
        }
        mProgress.dismiss();
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
