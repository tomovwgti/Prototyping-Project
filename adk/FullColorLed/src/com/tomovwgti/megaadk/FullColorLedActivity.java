/*
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

package com.tomovwgti.megaadk;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.tomovwgti.adk.R;
import com.tomovwgti.android.accessory.AccessoryBaseActivity;

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

        mLed = new LedLight();

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
