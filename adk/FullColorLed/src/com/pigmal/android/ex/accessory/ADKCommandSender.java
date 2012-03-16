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
    private static final String TAG = ADKCommandSender.class.getSimpleName();

    private ADKCommandSender mSender = null;
    private Accessory openAccessory;

    public ADKCommandSender(Accessory acc) {
        openAccessory = acc;
    }

    /**
     * 取得
     * 
     * @return
     */
    public ADKCommandSender getSender() {
        return mSender;
    }

    public void sendData(OutputData out) {
        out.sendData();
    }

    /**
     * Send a command to ADK
     * 
     * @param command
     * @param target
     * @param value
     */
    public void sendCommand(byte command, byte target, int value) {
        if (value > 255)
            value = 255;

        if (target != -1) {
            openAccessory.write(command, target, (byte) value);
        }
    }
}
