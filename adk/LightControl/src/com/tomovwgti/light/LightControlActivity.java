
package com.tomovwgti.light;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import de.roderick.weberknecht.WebSocketEventHandler;
import de.roderick.weberknecht.WebSocketMessage;

public class LightControlActivity extends Activity {
    static final String TAG = LightControlActivity.class.getSimpleName();

    private static String WS_URI = "ws://192.168.110.110:8001/";
    private AlertDialog mAlertDialog;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Activity activity;
    private Handler handler = new Handler();

    private SeekBar mRedLed;
    private SeekBar mGreenLed;
    private SeekBar mBlueLed;
    private TextView mRedText;
    private TextView mGreenText;
    private TextView mBlueText;

    private LedLight mLed;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        activity = this;

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();

        // IPアドレス確認ダイアログ
        mAlertDialog = showAlertDialog();
        mAlertDialog.show();

        mLed = new LedLight();

        mRedText = (TextView) findViewById(R.id.red);
        mRedLed = (SeekBar) findViewById(R.id.led_red);
        mRedLed.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mRedText.setText("RED : " + progress);
                mLed.red = progress;
                // mLed.sendData();
                mLed.sendWebSocket();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

        });

        mGreenText = (TextView) findViewById(R.id.green);
        mGreenLed = (SeekBar) findViewById(R.id.led_green);
        mGreenLed.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mGreenText.setText("GREEN : " + progress);
                mLed.green = progress;
                // mLed.sendData();
                mLed.sendWebSocket();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mBlueText = (TextView) findViewById(R.id.blue);
        mBlueLed = (SeekBar) findViewById(R.id.led_blue);
        mBlueLed.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mBlueText.setText("BLUE : " + progress);
                mLed.blue = progress;
                // mLed.sendData();
                mLed.sendWebSocket();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void setMessage(final String message, final int color) {
        // WebSocketHandlerのonMessageは別スレッドなのでhandlerを用いてviewの書き換えを行う
        handler.post(new Runnable() {
            @Override
            public void run() {
                // TextView messageArea = (TextView)
                // activity.findViewById(R.id.message_area);
                // messageArea.setText(message);
                // messageArea.setTextColor(color);
            }
        });
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        WebSocketManager.close();
        return super.clone();
    }

    private void connectWebSocket() {
        Log.i(TAG, "connect start");
        // WebSocket通信開始
        WebSocketManager.connect(WS_URI, new WebSocketEventHandler() {

            @Override
            public void onOpen() {
                Log.d(TAG, "websocket connect open");
            }

            @Override
            public void onMessage(WebSocketMessage message) {
                Log.d(TAG, "websocket message");
                String str = message.getText();
                String schema = Uri.parse(str).getScheme();

                if (schema.equals("command")) {
                    executeCommand(str);
                }
            }

            @Override
            public void onClose() {
                Log.d(TAG, "websocket connect close");
            }
        });
    }

    /**
     * コマンドを受けた時の処理
     */
    private void executeCommand(String str) {
        // ADKへ出力
        if (Uri.parse(str).getHost().equals("light")) {
            String rStr = Uri.parse(str).getQueryParameter("r");
            String gStr = Uri.parse(str).getQueryParameter("g");
            String bStr = Uri.parse(str).getQueryParameter("b");
            // LedLight light = new LedLight();
            // light.red = contains(rStr);
            // light.green = contains(gStr);
            // light.blue = contains(bStr);
            // light.sendData();
            // 変化を反映する
            mRedLed.setProgress(Integer.parseInt(rStr));
            mGreenLed.setProgress(Integer.parseInt(gStr));
            mBlueLed.setProgress(Integer.parseInt(bStr));
        }
    }

    private int contains(String color) {
        int value = 0;
        if (color == null) {
            return value;
        }
        value = Integer.parseInt(color);

        if (value < 0) {
            return 0;
        } else if (value > 255) {
            return 255;
        }
        return value;
    }

    private AlertDialog showAlertDialog() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View entryView = factory.inflate(R.layout.dialog_entry, null);
        final EditText edit = (EditText) entryView.findViewById(R.id.username_edit);

        if (pref.getString("IPADDRESS", "").equals("")) {
            edit.setHint("***.***.***.***");
        } else {
            edit.setText(pref.getString("IPADDRESS", ""));
        }
        // キーハンドリング
        edit.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // Enterキーハンドリング
                if (KeyEvent.KEYCODE_ENTER == keyCode) {
                    // 押したときに改行を挿入防止処理
                    if (KeyEvent.ACTION_DOWN == event.getAction()) {
                        return true;
                    }
                    // 離したときにダイアログ上の[OK]処理を実行
                    else if (KeyEvent.ACTION_UP == event.getAction()) {
                        if (edit != null && edit.length() != 0) {
                            // ここで[OK]が押されたときと同じ処理をさせます
                            String editStr = edit.getText().toString();
                            // OKボタン押下時のハンドリング
                            Log.v(TAG, editStr);
                            connectWebSocket();

                            // AlertDialogを閉じます
                            mAlertDialog.dismiss();
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        // AlertDialog作成
        return new AlertDialog.Builder(this).setTitle("Server IP Address").setView(entryView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String editStr = edit.getText().toString();
                        // OKボタン押下時のハンドリング
                        Log.v(TAG, editStr);
                        editor.putString("IPADDRESS", editStr);
                        editor.commit();
                        WS_URI = "ws://" + editStr + ":8001/";
                        connectWebSocket();
                    }
                }).create();
    }
}
