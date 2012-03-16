
package com.tomovwgti.megaadk;

import com.pigmal.android.ex.accessory.OutputController;

public class LedLight {
    private static final String TAG = LedLight.class.getSimpleName();

    private OutputController mOut;

    public LedLight(OutputController out) {
        mOut = out;
    }

    public int red;
    public int green;
    public int blue;

    public void setLed() {
        mOut.setLed(red, green, blue);
    }
}
