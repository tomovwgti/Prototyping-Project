/*
  Switch
  Turns on an LED on for one second, then off for one second, repeatedly.
 
  This example code is in the public domain.
 */

#define SWITCH  7
#define LED    13

void setup() {
  pinMode(SWITCH, INPUT);
  pinMode(LED, OUTPUT);
  digitalWrite(LED, LOW);

  Serial.begin(9600);
  Serial.println("start");
}

void loop() {
  int push = digitalRead(SWITCH);
  Serial.println(push);
  if ( push == LOW ) {
    digitalWrite(LED, HIGH);
  } else {
    digitalWrite(LED, LOW);
  }
  delay(1000);
}
