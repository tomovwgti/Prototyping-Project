
package com.tomovwgti.megaadk;

import com.pigmal.android.ex.accessory.ADKCommandSender;
import com.pigmal.android.ex.accessory.OutputData;

public class LedLight extends OutputData {

    public LedLight(ADKCommandSender sender) {
        super(sender);
    }

    private static final String TAG = LedLight.class.getSimpleName();

    // ADK device command
    private static final byte LED_COMMAND = 2;

    // ADK device parameter
    private static final byte RED_LED = 0;
    private static final byte GREEN_LED = 1;
    private static final byte BLUE_LED = 2;

    public int red;
    public int green;
    public int blue;

    @Override
    public void sendData() {
        mSender.sendCommand(LED_COMMAND, RED_LED, red);
        mSender.sendCommand(LED_COMMAND, GREEN_LED, green);
        mSender.sendCommand(LED_COMMAND, BLUE_LED, blue);
    }
}
