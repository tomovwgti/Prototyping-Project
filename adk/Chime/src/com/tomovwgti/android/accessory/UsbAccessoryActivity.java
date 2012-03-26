
package com.tomovwgti.android.accessory;

import android.content.Intent;

import com.tomovwgti.chime.ChimeActivity;

public final class UsbAccessoryActivity extends UsbAccessoryAbstractActivity {

    @Override
    protected Intent getMainActivity() {
        return new Intent(this, ChimeActivity.class);
    }
}
