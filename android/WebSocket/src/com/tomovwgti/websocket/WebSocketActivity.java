
package com.tomovwgti.websocket;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import de.roderick.weberknecht.WebSocketEventHandler;
import de.roderick.weberknecht.WebSocketMessage;

public class WebSocketActivity extends Activity {
    static final String TAG = WebSocketActivity.class.getSimpleName();

    private static String WS_URI = "ws://192.168.110.110:8001/";
    private static final String NULLPO_KEY = "ぬるぽ";
    private static final String GATT_KEY = "ｶﾞｯ";

    private static final String NULLPO_TEXT = "ぬるぽ ( ´∀｀)";
    private static final String GATT_TEXT = "ｶﾞｯ (ヽ'ω`)";

    private Handler handler = new Handler();
    private Activity activity;
    private AlertDialog mAlertDialog;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        activity = this;

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();

        // IPアドレス確認ダイアログ
        mAlertDialog = showAlertDialog();
        mAlertDialog.show();

        // ぬるぽボタン押下時の挙動
        Button nullpoBtn = (Button) findViewById(R.id.btn_null_btn);
        nullpoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebSocketManager.send(NULLPO_KEY);
                setMessage(NULLPO_TEXT, Color.BLUE);
            }
        });

        // ガッボタン押下時の挙動
        Button gattBtn = (Button) findViewById(R.id.btn_ga_btn);
        gattBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "gatt button clecked");
                WebSocketManager.send(GATT_KEY);
                setMessage(GATT_TEXT, Color.GREEN);
            }
        });

        // Mapボタン押下時の挙動
        Button mapBtn = (Button) findViewById(R.id.btn_map_btn);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebSocketManager.send("geo:36.744386,139.457703");
                setMessage(GATT_TEXT, Color.GREEN);
            }
        });
    }

    private void setMessage(final String message, final int color) {
        // WebSocketHandlerのonMessageは別スレッドなのでhandlerを用いてviewの書き換えを行う
        handler.post(new Runnable() {
            @Override
            public void run() {
                TextView messageArea = (TextView) activity.findViewById(R.id.message_area);
                messageArea.setText(message);
                messageArea.setTextColor(color);
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

                if (schema == null) {
                    if (NULLPO_KEY.equals(str)) {
                        setMessage(NULLPO_TEXT, Color.RED);
                    } else if (GATT_KEY.equals(str)) {
                        setMessage(GATT_TEXT, Color.YELLOW);
                    } else {
                        setMessage(str, Color.BLUE);
                    }
                } else {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    if (schema.equals("geo")) {
                        // Map呼び出し
                        intent.setData(Uri.parse(str + "?z=13"));
                    } else if (schema.equals("http")) {
                        // Browser呼び出し
                        intent.setData(Uri.parse(str));
                    }
                    startActivity(intent);
                }
            }

            @Override
            public void onClose() {
                Log.d(TAG, "websocket connect close");
            }
        });
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
