
package com.tomovwgti.socketio;

import io.socket.SocketIO;
import io.socket.util.SocketIOManager;
import net.arnx.jsonic.JSON;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tomovwgti.json.Geo;
import com.tomovwgti.json.Msg;
import com.tomovwgti.json.Value;

public class MainActivity extends Activity {
    static final String TAG = MainActivity.class.getSimpleName();

    private static final String UU_STR = "uu";
    private static final String NYAA_STR = "nyaa";

    private static final String UU_TEXT = "(」・ω・)」うー！";
    private static final String NYAA_TEXT = "(／・ω・)／にゃー！";

    private SocketIOManager mSocketManager;
    private SocketIO mSocket;

    private AlertDialog mAlertDialog;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SocketIOManager.SOCKETIO_DISCONNECT:
                    Log.i(TAG, "SOCKETIO_DISCONNECT");
                    Toast.makeText(MainActivity.this, "Disconnect", Toast.LENGTH_SHORT).show();
                    break;
                case SocketIOManager.SOCKETIO_CONNECT:
                    Log.i(TAG, "SOCKETIO_CONNECT");
                    Toast.makeText(MainActivity.this, "Connect", Toast.LENGTH_SHORT).show();
                    break;
                case SocketIOManager.SOCKETIO_HERTBEAT:
                    Log.i(TAG, "SOCKETIO_HERTBEAT");
                    break;
                case SocketIOManager.SOCKETIO_MESSAGE:
                    Log.i(TAG, "SOCKETIO_MESSAGE");
                    break;
                case SocketIOManager.SOCKETIO_JSON_MESSAGE:
                    Log.i(TAG, "SOCKETIO_JSON_MESSAGE");
                    Value value = JSON.decode((String) (msg.obj), Value.class);
                    receiveMessage(value.getValue());
                    break;
                case SocketIOManager.SOCKETIO_EVENT:
                    Log.i(TAG, "SOCKETIO_EVENT");
                    break;
                case SocketIOManager.SOCKETIO_ERROR:
                    Log.i(TAG, "SOCKETIO_ERROR");
                    break;
                case SocketIOManager.SOCKETIO_ACK:
                    Log.i(TAG, "SOCKETIO_ACK");
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);

        mSocketManager = new SocketIOManager(mHandler);

        // うーボタン押下時の挙動
        Button uuBtn = (Button) findViewById(R.id.btn_uu_btn);
        uuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Value value = new Value();
                Msg msg = new Msg();
                msg.setCommand("");
                msg.setSender("android");
                msg.setCommand("Message");
                msg.setMessage(UU_STR);
                value.setValue(msg);
                String message = JSON.encode(value);
                try {
                    mSocket.emit("message", new JSONObject(message));
                } catch (org.json.JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        // にゃーボタン押下時の挙動
        Button nyaaBtn = (Button) findViewById(R.id.btn_nyaa_btn);
        nyaaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Value value = new Value();
                Msg msg = new Msg();
                msg.setCommand("");
                msg.setSender("android");
                msg.setCommand("Message");
                msg.setMessage(NYAA_STR);
                value.setValue(msg);
                String message = JSON.encode(value);
                try {
                    mSocket.emit("message", new JSONObject(message));
                } catch (org.json.JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        // Mapボタン押下時の挙動
        Button mapBtn = (Button) findViewById(R.id.btn_map_btn);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Value value = new Value();
                Msg msg = new Msg();
                msg.setCommand("geo");
                msg.setMessage("Map");
                msg.setSender("android");
                Geo geo = new Geo();
                geo.setLat("36.744386");
                geo.setLon("139.457703");
                msg.setGeo(geo);
                value.setValue(msg);
                String message = JSON.encode(value);
                try {
                    mSocket.emit("message", new JSONObject(message));
                } catch (org.json.JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();

        // IPアドレス確認ダイアログ
        mAlertDialog = showAlertDialog();
        mAlertDialog.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSocketManager.disconnect();
    }

    private void receiveMessage(final Msg msg) {
        if (msg.getCommand() != null && msg.getCommand().equals("")) {
            if (UU_STR.equals(msg.getMessage())) {
                setMessage(UU_TEXT, Color.RED);
            } else if (NYAA_STR.equals(msg.getMessage())) {
                setMessage(NYAA_TEXT, Color.RED);
            } else {
                setMessage(msg.getMessage(), Color.BLUE);
            }
        } else if (msg.getCommand() != null && msg.getCommand().equals("geo")) {
            // Map呼び出し
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("geo:" + msg.getGeo().getLat() + "," + msg.getGeo().getLon()
                    + "?z=13"));
            startActivity(intent);
        } else if (msg.getCommand() != null && msg.getCommand().equals("http")) {
            // Browser呼び出し
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(msg.getMessage()));
            startActivity(intent);
        } else {
            // それ以外
            setMessage(msg.getMessage(), Color.GREEN);
        }
    }

    private void setMessage(final String message, final int color) {
        // WebSocketHandlerのonMessageは別スレッドなのでhandlerを用いてviewの書き換えを行う
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                TextView messageArea = (TextView) findViewById(R.id.message_area);
                messageArea.setText(message);
                messageArea.setTextColor(color);
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
                            mSocket = mSocketManager.connect("http://" + editStr + ":3000/");
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
                        mSocket = mSocketManager.connect("http://" + editStr + ":3000/");
                    }
                }).create();
    }
}
