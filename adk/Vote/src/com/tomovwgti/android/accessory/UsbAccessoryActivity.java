
package com.tomovwgti.android.accessory;

import android.content.Intent;

import com.tomovwgti.vote.VoteActivity;

public class UsbAccessoryActivity extends UsbAccessoryAbstractActivity {

    @Override
    protected Intent getMainActivity() {
        return new Intent(this, VoteActivity.class);
    }
}
