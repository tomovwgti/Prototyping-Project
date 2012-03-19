/*
  Blink

  13ピンのLEDを点滅させる
 */

#define LED  13

void setup() {
  // 13pinには1kΩの抵抗が入っているので直接つないでもOK
  pinMode(LED, OUTPUT);
}

void loop() {
  digitalWrite(LED, HIGH);   // set the LED on
  delay(1000);              // wait for a second
  digitalWrite(LED, LOW);    // set the LED off
  delay(1000);              // wait for a second
}
