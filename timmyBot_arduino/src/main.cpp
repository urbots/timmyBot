#include "Arduino.h"
#include "timmy.h"

/* Bluethoot comunication */
SoftwareSerial HC05 (11, 12);  //pin RX, pin TX

void setup() {
  Serial.begin(9600);        //Inicializa puerto serie por hard
  HC05.begin(9600);    //Inicializa puerto serie por soft
}

void loop() {
  String data;
  float linear, angular;
  data = ReadData(HC05);
  ConvertData(data, &linear, &angular);
  MoveRobot(linear, angular);
}
