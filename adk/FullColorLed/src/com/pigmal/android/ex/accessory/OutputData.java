
package com.pigmal.android.ex.accessory;

public abstract class OutputData {

    protected ADKCommandSender mSender = null;

    public OutputData(ADKCommandSender sender) {
        mSender = sender;
    }

    public abstract void sendData();
}
