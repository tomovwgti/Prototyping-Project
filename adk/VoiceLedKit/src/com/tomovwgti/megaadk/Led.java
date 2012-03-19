
package com.tomovwgti.megaadk;

import com.tomovwgti.android.accessory.io.OutputData;

public class Led extends OutputData {
    private static final String TAG = Led.class.getSimpleName();

    // ADK device command
    private static final byte BALSE_COMMAND = 3;

    public byte led = 0;

    @Override
    public void sendData() {
        sendCommand(BALSE_COMMAND, (byte) 0, led);
    }
}
