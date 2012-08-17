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

package com.tomovwgti.ledcontrol;

import io.socket.SocketIO;
import net.arnx.jsonic.JSON;

import org.json.JSONObject;

import com.tomovwgti.android.accessory.io.OutputData;
import com.tomovwgti.json.Light;
import com.tomovwgti.json.Msg;
import com.tomovwgti.json.Value;

public class LedLight extends OutputData {

    static final String TAG = LedLight.class.getSimpleName();

    // ADK device command
    private static final byte LED_COMMAND = 2;

    // ADK device parameter
    private static final byte RED_LED = 0;
    private static final byte GREEN_LED = 1;
    private static final byte BLUE_LED = 2;

    private SocketIO mSocket = null;
    public int red;
    public int green;
    public int blue;

    public LedLight(final SocketIO socketio) {
        this.mSocket = socketio;
    }

    @Override
    public void sendData() {
        sendCommand(LED_COMMAND, RED_LED, red);
        sendCommand(LED_COMMAND, GREEN_LED, green);
        sendCommand(LED_COMMAND, BLUE_LED, blue);
    }

    public void sendWebSocket() {
        Value value = new Value();
        Msg msg = new Msg();
        Light light = new Light();
        msg.setCommand("light");
        msg.setSender("android");
        light.setRed(red);
        light.setGreen(green);
        light.setBlue(blue);
        msg.setLight(light);
        value.setValue(msg);
        String message = JSON.encode(value);
        try {
            mSocket.emit("message", new JSONObject(message));
        } catch (org.json.JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
