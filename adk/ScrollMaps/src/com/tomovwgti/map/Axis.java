
package com.tomovwgti.map;

import android.app.Activity;

import com.tomovwgti.android.accessory.io.InputDataListener;

public class Axis implements InputDataListener {
    static final String TAG = Axis.class.getSimpleName();

    private final ScrollMapsActivity mActivity;
    public static final byte TYPE_AXIS = 0x07;

    public int x;
    public int y;
    public int z;

    public Axis(Activity activity, int x, int y, int z) {
        this.mActivity = (ScrollMapsActivity) activity;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void handleMassage() {
        mActivity.setAxisValue(x, y, z);
    }
}
