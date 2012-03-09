
package com.tomovwgti.vote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class VoteTestActivity extends Activity {
    private static final String TAG = VoteTestActivity.class.getSimpleName();
    private static final String URL = "http://pure-spring-5297.herokuapp.com/votes/4f2a3fb38529d00100000001/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final Map map = new HashMap<String, String>();

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.clear();
                map.put("selection", "えー");
                String response = httpPostRequest(URL, map, "UTF-8");
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.clear();
                map.put("selection", "やー");
                String response = httpPostRequest(URL, map, "UTF-8");
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.clear();
                map.put("selection", "たー");
                String response = httpPostRequest(URL, map, "UTF-8");
            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.clear();
                map.put("selection", "ぶー");
                String response = httpPostRequest(URL, map, "UTF-8");
            }
        });
    }

    /**
     * HTTP POSTを行う
     * 
     * @param url HTTP通信を行うターゲットのURL
     * @param requestParams パラメータ
     * @param encode HTTP要求時の文字コード
     * @return 受信結果の文字列  
     */
    public String httpPostRequest(String url, Map<String, String> requestParams, String encode) {
        try {
            HttpPost httppost = new HttpPost(url);
            DefaultHttpClient client = new DefaultHttpClient();

            // リクエストパラメータの設定
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : requestParams.entrySet()) {
                params.add(new BasicNameValuePair((String) entry.getKey(), (String) entry
                        .getValue()));
            }

            // POST データの設定
            httppost.setEntity(new UrlEncodedFormEntity(params, encode));

            HttpResponse response = client.execute(httppost);
            int status = response.getStatusLine().getStatusCode();

            // 結果が正しく帰って来なければエラー
            if (status != HttpStatus.SC_OK) {
                throw new Exception("");
            }
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            return null;
        }
    }
}