
package com.tomovwgti.megaadk;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.pigmal.android.accessory.AccessoryBaseActivity;
import com.tomovwgti.adk.R;

public class FullColorLedActivity extends AccessoryBaseActivity {
    private static final String TAG = FullColorLedActivity.class.getSimpleName();

    private SeekBar mRedLed;
    private SeekBar mGreenLed;
    private SeekBar mBlueLed;
    private TextView mRedText;
    private TextView mGreenText;
    private TextView mBlueText;

    private LedLight mLed;

    @Override
    protected void showControls() {
        setContentView(R.layout.main);

        mLed = new LedLight(mSender);

        mRedText = (TextView) findViewById(R.id.red);
        mRedLed = (SeekBar) findViewById(R.id.led_red);
        mRedLed.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mRedText.setText("RED : " + progress);
                mLed.red = progress;
                mSender.sendData(mLed);
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
                mSender.sendData(mLed);
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
                mSender.sendData(mLed);
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
    protected void hideControls() {
        setContentView(R.layout.no_device);
    }
}
