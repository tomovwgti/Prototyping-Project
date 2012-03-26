
package com.tomovwgti.chime;

import android.media.AudioManager;
import android.media.SoundPool;

import com.tomovwgti.android.accessory.AccessoryBaseActivity;
import com.tomovwgti.android.accessory.io.ADKCommandAbstractReciever;
import com.tomovwgti.android.accessory.io.ADKCommandReciever;

public class ChimeActivity extends AccessoryBaseActivity {

    private SoundPool mSp;
    private int mId;

    @Override
    protected void showControls() {
        setContentView(R.layout.main);

        mSp = new SoundPool(1, AudioManager.STREAM_RING, 0);
        mId = mSp.load(this, R.raw.chime, 1);
    }

    @Override
    protected ADKCommandAbstractReciever createReciever() {
        return new ADKCommandReciever(this);
    }

    public void setChime() {
        mSp.play(mId, 1.0F, 1.0F, 0, 0, 1.0F);
    }
}
