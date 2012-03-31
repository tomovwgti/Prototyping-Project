
package com.tomovwgti.vote;

import android.app.Activity;

import com.tomovwgti.android.accessory.io.InputDataListener;

public class Switch implements InputDataListener {
    private final static String TAG = Switch.class.getSimpleName();

    private final VoteActivity mActivity;
    public static final byte TYPE_SWITCH = 1;
    public int button = 0;

    public Switch(Activity activity, int button) {
        this.mActivity = (VoteActivity) activity;
        this.button = button;
    }

    @Override
    public void handleMassage() {
        if (button == 0) {
            mActivity.vote();
        }
    }
}
