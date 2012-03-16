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

import com.pigmal.android.accessory.Accessory;

/**
 * Control the user input and output to ADK
 * 
 * @author itog
 */
public class OutputController {
    private static final String TAG = OutputController.class.getSimpleName();

    private static final int RED_LED = 0;
    private static final int GREEN_LED = 1;
    private static final int BLUE_LED = 2;

    private ADKCommandSender adkSender;

    public OutputController(Accessory acc) {
        adkSender = new ADKCommandSender(acc);
    }

    public void setLed(int red, int green, int blue) {
        adkSender.sendLEDcommand(RED_LED, red);
        adkSender.sendLEDcommand(GREEN_LED, green);
        adkSender.sendLEDcommand(BLUE_LED, blue);
    }
}
