/*
  Switch
 */

#include <Max3421e.h>
#include <Usb.h>
#include <AndroidAccessory.h>

#define SWITCH  7
#define LED    13

AndroidAccessory acc("Google, Inc.",
		     "DemoKit",
		     "DemoKit Arduino Board",
		     "1.0",
		     "http://www.android.com",
		     "0000000012345678");

void setup() {
  pinMode(SWITCH, INPUT);
  pinMode(LED, OUTPUT);
  digitalWrite(LED, LOW);
  acc.powerOn();

  Serial.begin(9600);
  Serial.println("start");
}

void loop() {
  byte msg[2];

  if (acc.isConnected()) {

    byte push = digitalRead(SWITCH);
    Serial.println(push);
    if ( push == LOW ) {
      digitalWrite(LED, HIGH);
    } else {
      digitalWrite(LED, LOW);
    }

    msg[0] = 0x1;
    msg[1] = push;
    acc.write(msg, 2);
  }
  delay(200);
}
