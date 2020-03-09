#include "Arduino.h"
#include "timmy.h"

/* Bluetooth comunication */
SoftwareSerial HC05 (12, 11);  //pin RX, pin TX

void setup() {
  Serial.begin(9600);        //Inicializa puerto serie por hard
  HC05.begin(9600);    //Inicializa puerto serie por soft
  pinMode(13, OUTPUT);
  digitalWrite(13, HIGH);
  Serial.println("Empezamos loco!");
}

void loop() {
  String data;
  float linear, angular;
  char c='0';
  String s = "";

  while (c != '?')
  {
    if (HC05.available())
    {
      c = HC05.read();
      s.concat(c);
    }
  }
  if (s != "")
  {
    Serial.print("String: ");
    Serial.println(s);
  }
  
  ConvertData(s, &linear, &angular);
  if (linear != 0)
  {
    Serial.print("linear: ");
    Serial.println(linear);
  }
  if (angular != 0)
  {
    Serial.print("angular: ");
    Serial.println(angular);
  }
  
  MoveRobot(linear, angular);
}
