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

package com.tomovwgti.megaadk;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.pigmal.android.accessory.AccessoryBaseActivity;
import com.pigmal.android.ex.accessory.ADKCommandReceiver;
import com.pigmal.android.ex.accessory.OutputController;
import com.pigmal.android.util.Logger;

public class VoiceLedDemo extends AccessoryBaseActivity {
    private static final String TAG = VoiceLedDemo.class.getSimpleName();
    private ADKCommandReceiver mReceiver;
    public static final int REQUEST_CODE = 0;

    private OutputController mOutputController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mReceiver = new ADKCommandReceiver();

        if (mOpenAccessory.isConnected()) {
            showControls();
        } else {
            hideControls();
        }
    }

    @Override
    public void onDestroy() {
        mOpenAccessory.removeListener();
        super.onDestroy();
    }

    /**
     * Show controls on the display
     */
    private void showControls() {
        setContentView(R.layout.main);

        mOutputController = new OutputController(this, mOpenAccessory);
    }

    /**
     * Hide all controlls
     */
    private void hideControls() {
        setContentView(R.layout.no_device);
        mOutputController = null;
    }

    @Override
    protected void onUsbAtached() {
        Logger.v("onUsbAtached");
        showControls();
    }

    @Override
    protected void onUsbDetached() {
        Logger.v("onUsbDetached");
        hideControls();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals("Simulate")) {
            showControls();
        } else if (item.getTitle().equals("Quit")) {
            finish();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Simulate");
        menu.add("Quit");
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String RESULT_STRING = "バルス";

        // 自分が投げたインテントであれば応答する
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String resultsString = "";

            // 結果文字列リスト
            ArrayList<String> results = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            for (int i = 0; i < results.size(); i++) {
                // ここでは、文字列が複数あった場合に結合しています
                if (results.get(i).equals(RESULT_STRING)) {
                    mOutputController.setBalse(true);
                    Toast.makeText(this, results.get(i), Toast.LENGTH_LONG).show();
                    return;
                }
                resultsString += results.get(i);
            }

            // トーストを使って結果を表示
            Toast.makeText(this, resultsString, Toast.LENGTH_LONG).show();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
