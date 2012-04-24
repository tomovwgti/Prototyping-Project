/*
  Blink

  13ピンのLEDを点滅させる
 */

#define LED  13  // 13pinにLEDを接続

void setup() {  // 最初に１度だけ呼ばれる
  // 13pinには1kΩの抵抗が入っているので直接つないでもOK
  pinMode(LED, OUTPUT);  // LEDのピンを出力として設定
}

void loop() {
  digitalWrite(LED, HIGH);   // LEDピンにHIGHを出力
  delay(1000);               // 1秒間のウェイト
  digitalWrite(LED, LOW);    // LEDピンにLOWを出力
  delay(1000);               // 1秒間のウェイト
}
