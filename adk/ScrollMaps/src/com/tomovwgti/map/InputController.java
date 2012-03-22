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

package com.tomovwgti.map;

import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

/**
 * Control the view for sensor input display
 * 
 * @author itog
 */
public class InputController {
    protected Activity mHostActivity;
    private WebView mWebView;

    protected View findViewById(int id) {
        return mHostActivity.findViewById(id);
    }

    protected Resources getResources() {
        return mHostActivity.getResources();
    }

    InputController(Activity hostActivity, WebView webView) {
        mHostActivity = hostActivity;
        this.mWebView = webView;
    }

    public void setAxisValue(int xaxis, int yaxis, int zaxis) {
        // Log.i("AXIS", "X-AXIS: " + xaxis);
        // Log.i("AXIS", "Y-AXIS: " + yaxis);
        // Log.i("AXIS", "Z-AXIS: " + zaxis);
        // 誤差調整
        if (-10 < xaxis && xaxis < 10) {
            xaxis = 0;
        }
        if (-10 < yaxis && yaxis < 10) {
            yaxis = 0;
        }

        String text = "javascript:callJS(" + yaxis + "," + xaxis + ")";
        Log.i("AXIS", text);
        mWebView.loadUrl(text);
    }
}
