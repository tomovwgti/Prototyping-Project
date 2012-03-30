
package com.tomovwgti.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.arnx.jsonic.JSON;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.worldweatheronline.json.JsonObj;

public class HttpLoader extends AsyncTask<Void, Void, String> {
    private static final String TAG = HttpLoader.class.getSimpleName();

    public interface HttpListener {
        public void viewResult(String temp, String weather);
    }

    private static final String TARGET_URL = "free.worldweatheronline.com";
    private static final String KEY = "a75e78070e034532122203";

    private HttpListener mListener;
    private HttpURLConnection mHttp = null;
    private URL mUrl;

    public HttpLoader(HttpListener listener) {
        mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http");
        builder.authority(TARGET_URL);
        builder.path("/feed/weather.ashx");
        builder.appendQueryParameter("key", KEY);
        builder.appendQueryParameter("format", "json");
        try {
            mUrl = new URL(builder.toString() + "&q=35.85,139.52");
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(Void... params) {
        String jsonString = null;
        try {
            mHttp = (HttpURLConnection) mUrl.openConnection();
            mHttp.setRequestMethod("GET");
            mHttp.connect();
            // データを取得
            InputStream in = mHttp.getInputStream();
            jsonString = convertString(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        JsonObj jsonObj = JSON.decode(result, JsonObj.class);
        String temp = jsonObj.getData().getCurrentCondition().get(0).getTempC();
        String weather = jsonObj.getData().getCurrentCondition().get(0).getWeatherDesc().get(0)
                .getValue();
        Log.i(TAG,
                "URL "
                        + jsonObj.getData().getCurrentCondition().get(0).getWeatherIconUrl().get(0)
                                .getValue());
        mListener.viewResult(temp, weather);
    }

    /**
     * InputStream to String
     * 
     * @param is
     * @return
     * @throws IOException
     */
    private String convertString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while (null != (line = reader.readLine())) {
            sb.append(line);
        }
        return sb.toString();
    }
}
