
package com.tomovwgti.megaadk;

import java.util.ArrayList;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tomovwgti.android.accessory.AccessoryBaseActivity;

public class VoiceLedDemo extends AccessoryBaseActivity {
    private static final String TAG = VoiceLedDemo.class.getSimpleName();
    public static final int REQUEST_CODE = 0;

    private Led mLed;

    /**
     * Show controls on the display
     */
    @Override
    protected void showControls() {
        setContentView(R.layout.main);

        mLed = new Led();

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
                mLed.led = 0;
                mLed.sendData();
            }
        });
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
                    mLed.led = 1;
                    mLed.sendData();
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
