/*
 * Copyright (C) 2011 PIGMAL LLC
 *
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

package com.pigmal.android.ex.accessory;

import com.pigmal.android.accessory.Accessory;

/**
 * Send command to RT-ADK & RT-ADS hardware
 * 
 * @author itog
 */
public class ADKCommandSender {
    static final String TAG = "ADKCommandSender";

    public static final byte LED_COMMAND = 2;

    private Accessory openAccessory;

    public ADKCommandSender(Accessory acc) {
        openAccessory = acc;
    }

    /**
     * Send a command to ADK
     * 
     * @param command
     * @param target
     * @param value
     */
    private void sendCommand(byte command, byte target, int value) {
        if (value > 255)
            value = 255;

        if (target != -1) {
            openAccessory.write(command, target, (byte) value);
        }
    }

    public void sendLEDcommand(int target, int color_index, int value) {
        sendCommand(LED_COMMAND, (byte) ((target - 1) * 3 + color_index), (byte) value);
    }

    public void balseSequence(boolean flag) {
        final int out;
        if (flag == true) {
            out = 1;
        } else {
            out = 0;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    sendCommand((byte) 3, (byte) 0, out);
                    try {
                        Thread.sleep(1 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
