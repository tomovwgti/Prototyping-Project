
package com.tomovwgti.android.accessory.io;

import android.app.Activity;
import android.os.Message;
import android.util.Log;

import com.tomovwgti.vote.Switch;

public class ADKCommandReciever extends ADKCommandAbstractReciever {
    private static final String TAG = ADKCommandReciever.class.getSimpleName();

    private final Activity mActivity;

    public ADKCommandReciever(Activity activity) {
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
                        m.obj = new Switch(mActivity, buffer[i + 1]);
                        mHandler.sendMessage(m);
                    }
                    i += 4;
                    break;

                default:
                    Log.d(TAG, "unknown msg: " + buffer[i]);
                    i = len;
                    break;
            }
        }
    }
}
