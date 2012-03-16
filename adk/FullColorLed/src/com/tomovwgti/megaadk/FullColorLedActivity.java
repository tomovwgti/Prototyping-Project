
package com.tomovwgti.megaadk;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.pigmal.android.accessory.AccessoryBaseActivity;
import com.pigmal.android.ex.accessory.OutputController;
import com.tomovwgti.adk.R;

public class FullColorLedActivity extends AccessoryBaseActivity {
    private static final String TAG = FullColorLedActivity.class.getSimpleName();

    private OutputController mOutputController;

    private SeekBar mRedLed;
    private SeekBar mGreenLed;
    private SeekBar mBlueLed;
    private TextView mRedText;
    private TextView mGreenText;
    private TextView mBlueText;

    private LedLight mLed;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mOpenAccessory.isConnected()) {
            showControls();
        } else {
            hideControls();
        }
    }

    private void showControls() {
        setContentView(R.layout.main);

        mOutputController = new OutputController(mOpenAccessory);
        mLed = new LedLight(mOutputController);

        mRedText = (TextView) findViewById(R.id.red);
        mRedLed = (SeekBar) findViewById(R.id.led_red);
        mRedLed.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mRedText.setText("RED : " + progress);
                mLed.red = progress;
                mLed.setLed();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

        });

        mGreenText = (TextView) findViewById(R.id.green);
        mGreenLed = (SeekBar) findViewById(R.id.led_green);
        mGreenLed.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mGreenText.setText("GREEN : " + progress);
                mLed.green = progress;
                mLed.setLed();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mBlueText = (TextView) findViewById(R.id.blue);
        mBlueLed = (SeekBar) findViewById(R.id.led_blue);
        mBlueLed.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mBlueText.setText("BLUE : " + progress);
                mLed.blue = progress;
                mLed.setLed();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @Override
    public void onDestroy() {
        mOpenAccessory.removeListener();
        super.onDestroy();
    }

    private void hideControls() {
        setContentView(R.layout.no_device);
        mOutputController = null;
    }

    @Override
    protected void onUsbAtached() {
        Log.v(TAG, "onUsbAtached");
        showControls();
    }

    @Override
    protected void onUsbDetached() {
        Log.v(TAG, "onUsbDetached");
        hideControls();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals("Simulate")) {
            showControls();
        } else if (item.getTitle().equals("Quit")) {
            finish();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Simulate");
        menu.add("Quit");
        return true;
    }
}
