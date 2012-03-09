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

import android.os.Handler;
import android.os.Message;
import android.widget.SeekBar;

import com.pigmal.android.accessory.AccessoryListener;
import com.pigmal.android.util.Logger;

/**
 * Receive message from ADK and display on the device display
 * 
 * @author itog
 */
public class ADKCommandReceiver implements AccessoryListener {
    /**
     * message ids defined in RT-ADK firmware
     */
    public static final int TYPE_SWITCH = 0x01;
    public static final int TYPE_TEMPERATURE = 0x04;
    public static final int TYPE_LIGHT = 0x05;
    public static final int TYPE_JOYSTICK = 0x06;
    public static final int TYPE_AXIS = 0x07;

    private static final int MESSAGE_SWITCH = 1;
    private static final int MESSAGE_TEMPERATURE = 2;
    private static final int MESSAGE_LIGHT = 3;
    private static final int MESSAGE_JOY = 4;

    protected class SwitchMsg {
        private byte sw;
        private byte state;

        public SwitchMsg(byte sw, byte state) {
            this.sw = sw;
            this.state = state;
        }

        public byte getSw() {
            return sw;
        }

        public byte getState() {
            return state;
        }
    }

    protected class TemperatureMsg {
        private int temperature;

        public TemperatureMsg(int temperature) {
            this.temperature = temperature;
        }

        public int getTemperature() {
            return temperature;
        }
    }

    protected class LightMsg {
        private int light;

        public LightMsg(int light) {
            this.light = light;
        }

        public int getLight() {
            return light;
        }
    }

    protected class JoyMsg {
        private int x;
        private int y;

        public JoyMsg(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    protected class AxisMsg {
        private int x;
        private int y;
        private int z;

        public AxisMsg(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getXaxis() {
            return x;
        }

        public int getYaxis() {
            return y;
        }

        public int getZaxis() {
            return z;
        }
    }

    private int composeInt(byte hi, byte lo) {
        return ((hi & 0xff) << 8) + (lo & 0xff);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            }
        }
    };

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onAccessoryMessage(byte[] buffer) {
        int i = 0;
        int ret = buffer.length;

        while (i < ret) {
            int len = ret - i;

            switch (buffer[i]) {
                case TYPE_SWITCH:
                    if (len >= 3) {
                        Message m = Message.obtain(mHandler, MESSAGE_SWITCH);
                        m.obj = new SwitchMsg(buffer[i + 1], buffer[i + 2]);
                        mHandler.sendMessage(m);
                    }
                    i += 3;
                    break;

                case TYPE_TEMPERATURE:
                    if (len >= 3) {
                        Message m = Message.obtain(mHandler, MESSAGE_TEMPERATURE);
                        m.obj = new TemperatureMsg(composeInt(buffer[i + 1], buffer[i + 2]));
                        mHandler.sendMessage(m);
                    }
                    i += 3;
                    break;

                case TYPE_LIGHT:
                    if (len >= 3) {
                        Message m = Message.obtain(mHandler, MESSAGE_LIGHT);
                        m.obj = new LightMsg(composeInt(buffer[i + 1], buffer[i + 2]));
                        mHandler.sendMessage(m);
                    }
                    i += 3;
                    break;

                case TYPE_JOYSTICK:
                    if (len >= 3) {
                        Message m = Message.obtain(mHandler, MESSAGE_JOY);
                        m.obj = new JoyMsg(buffer[i + 1], buffer[i + 2]);
                        mHandler.sendMessage(m);
                    }
                    i += 3;
                    break;

                default:
                    Logger.d("unknown msg: " + buffer[i]);
                    i = len;
                    break;
            }
        }
    }
}
