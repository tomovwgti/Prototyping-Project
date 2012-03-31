
package com.tomovwgti.android.accessory;

import android.content.Intent;

import com.tomovwgti.android.accessory.UsbAccessoryAbstractActivity;
import com.tomovwgti.weather.WeatherReportActivity;

public final class UsbAccessoryActivity extends UsbAccessoryAbstractActivity {

    @Override
    protected Intent getMainActivity() {
        return new Intent(this, WeatherReportActivity.class);
    }
}
