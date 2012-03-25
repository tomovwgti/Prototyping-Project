
package com.tomovwgti.android.accessory.io;

import android.app.Activity;
import android.os.Message;
import android.util.Log;

import com.tomovwgti.android.accessory.io.ADKCommandAbstractReciever;
import com.tomovwgti.map.Axis;

public class ADKCommandReciever extends ADKCommandAbstractReciever {
    private static final String TAG = ADKCommandReciever.class.getSimpleName();

    private final Activity mActivity;

    public ADKCommandReciever(Activity activity) {
        Log.i(TAG, "Const ADKCommandReceiver");
        this.mActivity = activity;
    }

    public void onAccessoryMessage(byte[] buffer) {
        int i = 0;
        int ret = buffer.length;
        Log.i("MESSAGE", "length: " + ret);

        while (i < ret) {
            int len = ret - i;
            Log.i(TAG, "buffer: " + buffer[i]);

            switch (buffer[i]) {
                case Axis.TYPE_AXIS:
                    if (len >= 3) {
                        Message m = Message.obtain(mHandler, Axis.TYPE_AXIS);
                        int x = buffer[i + 1];
                        int y = buffer[i + 2];

                        m.obj = new Axis(mActivity, x, y, 0);
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
