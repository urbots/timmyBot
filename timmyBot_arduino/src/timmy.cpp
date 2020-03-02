#include "timmy.h"
#include "Kinematics.h"
#include <Arduino.h>
#include <DRV8835MotorShield.h>
#include <SoftwareSerial.h>

Kinematics kinematics(MOTOR_MAX_RPM, WHEEL_DIAMETER, FR_WHEEL_DISTANCE, 
                        LR_WHEEL_DISTANCE, PWM_BITS);

String ReadData(SoftwareSerial bt)
{
  String s = "";
  char c = 0;
  while (bt.available() && c!= '\r')
  {
    c = bt.read();
    s.concat(c);
  }
  return s;
}

void ConvertData(String data, float *linear, float *angular)
{

}

void MoveRobot(float linear, float angular)
{
  Kinematics::output rpm;
  rpm = kinematics.getRPM(linear, 0, angular);
}
