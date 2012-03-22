#include <Max3421e.h>
#include <Usb.h>
#include <AndroidAccessory.h>

// 加速度センサのx軸とy軸に接続したアナログのピン番号
#define X_AXIS A0
#define Y_AXIS A1

AndroidAccessory acc("Google, Inc.",
		     "DemoKit",
		     "DemoKit Arduino Board",
		     "1.0",
		     "http://www.android.com",
		     "0000000012345678");

void setup()
{
	Serial.begin(9600);
	Serial.print("Start");
	acc.powerOn();
}

void loop()
{
  byte msg[3];

  if (acc.isConnected()) {
    int x, y;

    read_axis(&x, &y);
    msg[0] = 0x7;
    msg[1] = constrain(x, -180, 180);
    msg[2] = constrain(y, -180, 180);
    acc.write(msg, 3);
  }
  
  delay(100);
}

void read_axis(int *x, int *y) 
{
  // x軸とy軸の値を読み取る
  int xAxisValue = analogRead(X_AXIS);
  int yAxisValue = analogRead(Y_AXIS);
  
  // 読み取った値を-1から1までの範囲にスケーリングしてsinθの値とする
  float xAxisSinTheta = mapInFloat(xAxisValue, 306, 716, -1, 1);
  float yAxisSinTheta = mapInFloat(yAxisValue, 306, 716, -1, 1);
  
  // それぞれの値を-1から1までの範囲に制限する
  xAxisSinTheta = constrain(xAxisSinTheta, -1, 1);
  yAxisSinTheta = constrain(yAxisSinTheta, -1, 1);

  // 逆サインを求めた結果（単位はラジアン）を度に変換
  *x = floor(asin(xAxisSinTheta) * 180 / PI);
  *y = floor(asin(yAxisSinTheta) * 180 / PI);
  
  Serial.print("X axis ");
  Serial.println(*x);
  Serial.print("Y axis ");
  Serial.println(*y);
}

// 標準で用意されているmapは引数と戻り値の型がlongである。
// 今回は-1から1までにスケーリングする必要があるためfloatで同じ計算をする
float mapInFloat(float x, float iMin, float iMax, float oMin, float oMax) {
  return (x -iMin) * (oMax - oMin) / (iMax - iMin) + oMin;
}
