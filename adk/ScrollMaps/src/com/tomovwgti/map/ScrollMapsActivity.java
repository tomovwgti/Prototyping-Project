
package com.tomovwgti.map;

import android.util.Log;
import android.webkit.WebView;

import com.tomovwgti.android.accessory.AccessoryBaseActivity;
import com.tomovwgti.android.accessory.io.ADKCommandAbstractReceiver;
import com.tomovwgti.android.accessory.io.ADKCommandReciever;

public class ScrollMapsActivity extends AccessoryBaseActivity {
    private static final String TAG = ScrollMapsActivity.class.getSimpleName();

    private String URL = "http://dl.dropbox.com/u/589955/MapSample.html";
    private WebView mWebView;

    /**
     * Show controls on the display
     */
    @Override
    protected void showControls() {
        setContentView(R.layout.main);

        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(URL);
    }

    @Override
    protected ADKCommandAbstractReceiver createReciever() {
        return new ADKCommandReciever(this);
    }

    public void setAxisValue(int xaxis, int yaxis, int zaxis) {
        // Log.i("AXIS", "X-AXIS: " + xaxis);
        // Log.i("AXIS", "Y-AXIS: " + yaxis);
        // Log.i("AXIS", "Z-AXIS: " + zaxis);
        // 誤差調整
        if (-10 < xaxis && xaxis < 10) {
            xaxis = 0;
        }
        if (-10 < yaxis && yaxis < 10) {
            yaxis = 0;
        }

        String text = "javascript:callJS(" + yaxis + "," + xaxis + ")";
        Log.i("AXIS", text);
        mWebView.loadUrl(text);
    }

    public void callJavaScript(String data) {
        mWebView.loadUrl(data);
    }
}
