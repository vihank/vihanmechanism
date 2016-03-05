/* Copyright (c) 2014 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * TeleOp Modefjf
 * <p>
 * Enables control of the robot via the gamepad
 */
public class STTeleop extends OpMode {


	DcMotor motorPivot;
	DcMotor motorRight;
	DcMotor motorLeft;
	DcMotor motorScoop;
	//DcMotor motorHookLeft;
	//DcMotor motorHookRight;
	Servo flapRight;
	Servo flapLeft;

	public STTeleop() {

	}


	@Override


        public void init() {
		/*
		 * in this part of our program, we map the motors to certain parts of our robot, linking
		 * them to certain parts via the phones configuration
		 */

		motorRight = hardwareMap.dcMotor.get("motor_2");
		motorLeft = hardwareMap.dcMotor.get("motor_1");
		motorLeft.setDirection(DcMotor.Direction.REVERSE);
		motorScoop = hardwareMap.dcMotor.get("scoop");
		motorPivot = hardwareMap.dcMotor.get("Scoop_pivot");
		//motorHookRight = hardwareMap.dcMotor.get("hook_2");
		//motorHookLeft = hardwareMap.dcMotor.get("hook_1");
		//motorHookLeft.setDirection(DcMotor.Direction.REVERSE);
		flapRight = hardwareMap.servo.get("servo_3");
		flapLeft = hardwareMap.servo.get("servo_2");
        flapLeft.setDirection(Servo.Direction.REVERSE);
        flapRight.setDirection(Servo.Direction.REVERSE);
        //motorScoop.setDirection(DcMotor.Direction.REVERSE);

	}

	@Override
	public void loop() {
        //this area of our program makes our robot move, using these commands, it defines motors,
        //tells the phone which motor is set to which button, and sets the power levels.
        float left = gamepad1.left_stick_y;
		float right = gamepad1.right_stick_y;
		float scooping = gamepad2.left_stick_y;
        float pivoting = gamepad2.right_stick_y;

		right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);
		scooping = Range.clip(scooping, -1, 1);
		pivoting = Range.clip(pivoting, -1  , 1);
		// scale the joystick value to make it easier to control
		// the robot more precisely at slower speeds.
		right = (float)scaleInput(right);
		left =  (float)scaleInput(left);
        scooping = (float) scaleInput(scooping);
		pivoting = (float) scaleInput(pivoting);

		motorRight.setPower(right);
		motorLeft.setPower(left);
		motorScoop.setPower(scooping);
		motorPivot.setPower(pivoting*0.25);

                if(gamepad2.x) {
                    flapLeft.setPosition(1);
                }
                if(gamepad2.b) {
                    flapRight.setPosition(1);
                }
                if (gamepad2.y){
                    flapRight.setPosition(.5);
                }
                if (gamepad2.a){
                    flapLeft.setPosition(.5);
                }
                if (gamepad1.right_bumper){
                    motorRight.setDirection(DcMotor.Direction.REVERSE);
                    motorLeft.setDirection(DcMotor.Direction.REVERSE);
                }
    }


	@Override
	public void stop() {

	}

    	

	double scaleInput(double dVal)  {
		double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
				0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

		// get the corresponding index for the scaleInput array.
		int index = (int) (dVal * 16.0);

		// index should be positive.
		if (index < 0) {
			index = -index;
		}

		// index cannot exceed size of array minus 1.
		if (index > 16) {
			index = 16;
		}

		// get value from the array.
		double dScale = 0.0;
		if (dVal < 0) {
			dScale = -scaleArray[index];
		} else {
			dScale = scaleArray[index];
		}

		// return scaled value.
		return dScale;
	}

}
