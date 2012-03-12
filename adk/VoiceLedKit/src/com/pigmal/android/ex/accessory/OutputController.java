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

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.pigmal.android.accessory.Accessory;
import com.tomovwgti.megaadk.R;
import com.tomovwgti.megaadk.VoiceLedDemo;

/**
 * Control the user input and output to ADK
 * 
 * @author itog
 */
public class OutputController {
    private static final String TAG = OutputController.class.getSimpleName();
    private Button balseButton;
    private Button offButton;

    private Activity hostActivity;
    private ADKCommandSender adkSender;

    public OutputController(Activity activity, Accessory acc) {
        hostActivity = activity;
        adkSender = new ADKCommandSender(acc);

        balseButton = (Button) findViewById(R.id.balse);
        balseButton.setOnClickListener(balseListener);
        offButton = (Button) findViewById(R.id.off);
        offButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBalse(false);
            }
        });
    }

    OnClickListener balseListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                // インテント作成
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "VoiceRecognitionTest");

                // インテント発行
                hostActivity.startActivityForResult(intent, VoiceLedDemo.REQUEST_CODE);
            } catch (ActivityNotFoundException e) {
                // このインテントに応答できるアクティビティがインストールされていない場合
                Toast.makeText(v.getContext(), "ActivityNotFoundException", Toast.LENGTH_LONG)
                        .show();
            }
        }
    };

    private View findViewById(int id) {
        return hostActivity.findViewById(id);
    }

    public void setBalse(boolean flag) {
        adkSender.balseSequence(flag);
    }
}
