
package com.tomovwgti.chime;

import android.app.Activity;

import com.tomovwgti.android.accessory.io.InputDataListener;

public class Switch implements InputDataListener {
    private static final String TAG = Switch.class.getSimpleName();

    private final ChimeActivity mActivity;
    public static final byte TYPE_SWITCH = 1;
    public int button = 0;

    public Switch(Activity activity, int button) {
        this.mActivity = (ChimeActivity) activity;
        this.button = button;
    }

    @Override
    public void handleMassage() {
        if (button == 0) {
            mActivity.setChime();
        }
    }
}
