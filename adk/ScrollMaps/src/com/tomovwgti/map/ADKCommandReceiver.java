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

package com.tomovwgti.map;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.pigmal.android.util.Logger;
import com.tomovwgti.android.accessory.AccessoryListener;

/**
 * Receive message from ADK and display on the device display
 * 
 * @author itog
 */
public class ADKCommandReceiver implements AccessoryListener {
    /**
     * message ids defined in RT-ADK firmware
     */
    public static final int TYPE_AXIS = 0x07;

    private static final int MESSAGE_AXIS = 5;

    private InputController mInputController;

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
                case MESSAGE_AXIS:
                    AxisMsg a = (AxisMsg) msg.obj;
                    handleAxisMessage(a);
                    break;
            }
        }
    };

    protected void handleAxisMessage(AxisMsg axis) {
        if (mInputController != null) {
            mInputController.setAxisValue(axis.getXaxis(), axis.getYaxis(), axis.getZaxis());
        }
    }

    /**
     * Connect input controller so that the sensor value will be shown on the
     * display
     * 
     * @param controller
     */
    public void setInputController(InputController controller) {
        mInputController = controller;
    }

    /**
     * Disconnect input controller then stop to display
     */
    public void removeInputController() {
        mInputController = null;
    }

    @Override
    public void onAccessoryMessage(byte[] buffer) {
        int i = 0;
        int ret = buffer.length;
        Log.i("MESSAGE", "length: " + ret);

        while (i < ret) {
            int len = ret - i;

            switch (buffer[i]) {
                case TYPE_AXIS:
                    if (len >= 3) {
                        Message m = Message.obtain(mHandler, MESSAGE_AXIS);
                        int x = buffer[i + 1];
                        int y = buffer[i + 2];

                        m.obj = new AxisMsg(x, y, 0);
                        mHandler.sendMessage(m);
                    }
                    i += 4;
                    break;

                default:
                    Logger.d("unknown msg: " + buffer[i]);
                    i = len;
                    break;
            }
        }
    }
}
