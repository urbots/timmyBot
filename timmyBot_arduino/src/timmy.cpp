#include "timmy.h"
#include "Kinematics.h"
#include <Arduino.h>
#include <DRV8835MotorShield.h>
#include <SoftwareSerial.h>

Kinematics kinematics(MOTOR_MAX_RPM, WHEEL_DIAMETER, FR_WHEEL_DISTANCE, 
                        LR_WHEEL_DISTANCE, PWM_BITS);

DRV8835MotorShield motors;

void ConvertData(String data, float *linear, float *angular)
{
  (*linear) = data.substring(0, data.indexOf('#')).toFloat();
  (*angular) = data.substring(data.indexOf('#')+1,data.length()-1).toFloat();
}

void MoveRobot(float linear, float angular)
{
  Kinematics::output rpm;
  rpm = kinematics.getRPM(linear, 0, angular);
  int left_motor = rpm.motor1 * POLOLU_MAX / MOTOR_MAX_RPM;
  int right_motor = rpm.motor2 * POLOLU_MAX / MOTOR_MAX_RPM;
  motors.setM1Speed(left_motor);
  motors.setM2Speed(right_motor);
}
