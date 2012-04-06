
package com.tomovwgti.android.accessory.io;

import android.app.Activity;
import android.os.Message;
import android.util.Log;

import com.tomovwgti.chime.Switch;

public class ADKCommandReceiver extends ADKCommandAbstractReceiver {
    private static final String TAG = ADKCommandReceiver.class.getSimpleName();

    private final Activity mActivity;

    public ADKCommandReceiver(Activity activity) {
        this.mActivity = activity;
    }

    public void onAccessoryMessage(byte[] buffer) {
        int i = 0;
        int ret = buffer.length;

        while (i < ret) {
            int len = ret - i;

            switch (buffer[i]) {
                case Switch.TYPE_SWITCH:
                    if (len >= 1) {
                        Message m = Message.obtain(mHandler, Switch.TYPE_SWITCH);
                        int onOff = buffer[i + 1];
                        m.obj = new Switch(mActivity, onOff);
                        mHandler.sendMessage(m);
                    }
                    i += 2;
                    break;

                default:
                    Log.d(TAG, "unknown msg: " + buffer[i]);
                    i = len;
                    break;
            }
        }
    }
}
