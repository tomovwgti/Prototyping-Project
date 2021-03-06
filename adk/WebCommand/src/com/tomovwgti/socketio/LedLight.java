/*
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

package com.tomovwgti.socketio;

import android.util.Log;

import com.tomovwgti.android.accessory.io.OutputData;

public class LedLight extends OutputData {
    static final String TAG = LedLight.class.getSimpleName();

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
        log();
        sendCommand(LED_COMMAND, RED_LED, red);
        sendCommand(LED_COMMAND, GREEN_LED, green);
        sendCommand(LED_COMMAND, BLUE_LED, blue);
    }

    private void log() {
        Log.i(TAG, "RED  : " + red);
        Log.i(TAG, "GREEN: " + green);
        Log.i(TAG, "BLUE : " + blue);
    }
}
