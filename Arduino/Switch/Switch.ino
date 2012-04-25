/*
  Switch

  スイッチ押してる間LEDが点灯する
 */

#define SWITCH  2
#define LED    13
#define INTERRUPT 0

volatile int state = LOW;

void setup() {
  pinMode(SWITCH, INPUT);
//  attachInterrupt(INTERRUPT, interrupt, LOW);

  pinMode(LED, OUTPUT);
  digitalWrite(LED, LOW);

  Serial.begin(9600);      // シリアルに9600bpsで出力設定
  Serial.println("start"); // デバッグ出力
}

void loop() {
  state = digitalRead(SWITCH);
  Serial.println(state);

  polling();

  delay(100);
}

// ポーリング
void polling() {
  if ( state == LOW ) {
    digitalWrite(LED, HIGH);
  } else {
    digitalWrite(LED, LOW);
  }
}

// 割り込みルーチン
void interrupt() {
  digitalWrite(LED, HIGH);
  delayMicroseconds(10000);
  digitalWrite(LED, LOW);
}
