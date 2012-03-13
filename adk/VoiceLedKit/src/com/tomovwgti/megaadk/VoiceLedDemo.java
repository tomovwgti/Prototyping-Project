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

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pigmal.android.accessory.AccessoryBaseActivity;
import com.pigmal.android.ex.accessory.OutputController;

public class VoiceLedDemo extends AccessoryBaseActivity {
    private static final String TAG = VoiceLedDemo.class.getSimpleName();
    public static final int REQUEST_CODE = 0;

    private OutputController mOutputController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        mOutputController = new OutputController(mOpenAccessory);

        Button balseButton = (Button) findViewById(R.id.balse);
        balseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // インテント作成
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "VoiceRecognitionTest");
                    // インテント発行
                    startActivityForResult(intent, VoiceLedDemo.REQUEST_CODE);
                } catch (ActivityNotFoundException e) {
                    // このインテントに応答できるアクティビティがインストールされていない場合
                    Toast.makeText(v.getContext(), "ActivityNotFoundException", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
        Button offButton = (Button) findViewById(R.id.off);
        offButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOutputController.setBalse(false);
            }
        });
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
        Log.v(TAG, "onUsbAtached");
        showControls();
    }

    @Override
    protected void onUsbDetached() {
        Log.v(TAG, "onUsbDetached");
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
