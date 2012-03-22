
package com.tomovwgti.map;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Toast;

import com.pigmal.android.util.Logger;
import com.tomovwgti.android.accessory.AccessoryBaseActivity;

public class ScrollMapsActivity extends AccessoryBaseActivity {
    private String URL = "http://dl.dropbox.com/u/589955/MapSample.html";
    private WebView mWebView;
    private ADKCommandReceiver mReceiver;
    private InputController mInputController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        mReceiver = new ADKCommandReceiver();
        mOpenAccessory.setListener(mReceiver);

        if (mOpenAccessory.isConnected()) {
            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
            showControls();

        } else {
            mReceiver.removeInputController();
            mInputController = null;
            Toast.makeText(this, "Not Connected", Toast.LENGTH_SHORT).show();
        }

        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(URL);

    }

    public void callJavaScript(String data) {
        mWebView.loadUrl(data);
    }

    /**
     * Show controls on the display
     */
    private void showControls() {
        mInputController = new InputController(this, mWebView);
        mReceiver.setInputController(mInputController);
    }

    @Override
    protected void onUsbAtached() {
        Logger.v("onUsbAtached");
        showControls();
    }

    @Override
    protected void onUsbDetached() {
        Logger.v("onUsbDetached");
        mReceiver.removeInputController();
        mInputController = null;
    }

    @Override
    public void onDestroy() {
        mOpenAccessory.removeListener();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals("Quit")) {
            finish();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Quit");
        return true;
    }
}
