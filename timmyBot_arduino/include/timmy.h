#ifndef TIMMY_H
#define TIMMY_H

#include "Arduino.h"
#include <SoftwareSerial.h>

/* timmy parameters for kinematics */
#define MOTOR_MAX_RPM 8200
#define WHEEL_DIAMETER 0.0489     // expressed in meters
#define FR_WHEEL_DISTANCE 0       // front-rear wheel distance (meters)
#define LR_WHEEL_DISTANCE 0.0753  // left-right wheel distance (meters)

/* driver conf */
#define PWM_BITS 8  //PWM pin resolution Arduino Uno/Mega is using 8 bits(0-255)
#define POLOLU_MAX 50 // max value for pololu driver

String ReadData(SoftwareSerial bt);
void ConvertData(String data, float *linear, float *angular);
void MoveRobot(float linear, float angular);

#endif