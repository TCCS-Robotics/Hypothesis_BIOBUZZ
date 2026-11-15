package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.SharedClasses.RobotHardware;

@Autonomous(name = "Auto with Functions", group = "JH")
public class Auto extends LinearOpMode {
    private final RobotHardware robot = new RobotHardware();


    @Override
    public void runOpMode() {
    ///////////////////////////////////   Initialization Code Goes Here   /////////////////////////////
        robot.init(hardwareMap); //Hardware Map

        // Set motors to run using encoders
        robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //Telemetry


        waitForStart();
    ///////////////////////////////////   AUTO CODE GOES HERE   /////////////////////////////
        if (!opModeIsActive()) return;
        //Drive functions has parameters for Direction, target distance in inches and speed - Directions are
        //    FORWARD,
        //    BACKWARD,
        //    LEFT,
        //    RIGHT,
        //    CLOCKWISE,
        //    COUNTERCLOCKWISE,
        Drive(directions.CLOCKWISE,19,.5);




    ///////////////////////////////////   Telemetry   /////////////////////////////

    }//Closes Run OpMode

//////////////////////   FUNCTIONS GO HERE   /////////////////////////////
private void Drive(directions directions, int target, double speed){
    //goBILDA 19.2:1 motor has 537.7 ticks per revolution
    // 96 mm wheeel diameter
    // 538.7 tickes / revolution * 1 rev / (96*pi) mm * 1 mm / (0.0393701) in = 45.28479571 ticks per revolution

    int inches = 43;

    //Reset the encoders
        robot.backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    //Set case for which direction to change motor directions
      // 1 = Forward
      // -1 = Backward
    //Start them all forward - per the hardware map
    int FL = 1;
    int FR = 1;
    int BL = 1;
    int BR = 1;

    switch (directions){
        case FORWARD:
            //all motors forward
            break;
        case BACKWARD:
            //all motors backward
            FL = -1; FR = -1; BL = -1; BR = -1;
            break;
        case LEFT:
            //Strafe Left - Left side toward each other and Right side away from each other
            FL = -1; FR = 1; BL = 1; BR = -1;
            break;
        case RIGHT:
            //Strafe Right - Right side toward each other and Left side away from each other
            FL = 1; FR = -1; BL = -1; BR = 1;
            break;
        case CLOCKWISE:
            //Rotate Clockwise - Left side forward and right side backward
            FL = 1; FR = -1; BL = 1; BR = -1;
            break;
        case COUNTERCLOCKWISE:
            //Rotate CounterClockwise - Right side forward and left side backward
            FL = -1; FR = 1; BL = -1; BR = 1;
            break;
    }

    //Set Target Position
    robot.backLeftDrive.setTargetPosition(target * inches * BL);
    robot.backRightDrive.setTargetPosition(target * inches * BR);
    robot.frontLeftDrive.setTargetPosition(target * inches * FL);
    robot.frontRightDrive.setTargetPosition(target * inches * FR);

    //go to target
    robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    robot.backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    //Turn on motor
    robot.backLeftDrive.setPower(speed * BL);
    robot.backRightDrive.setPower(speed * BR);
    robot.frontLeftDrive.setPower(speed * FL);
    robot.frontRightDrive.setPower(speed * FR);

    //While loop to keep code running until it reaches the target
    while (opModeIsActive() && (robot.frontRightDrive.isBusy() || robot.frontLeftDrive.isBusy()) ){

    }

    //reset encoders
    robot.backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

}// ends Drive Function
enum directions{
    FORWARD,
    BACKWARD,
    LEFT,
    RIGHT,
    CLOCKWISE,
    COUNTERCLOCKWISE,
}



}//Closes Linear OpMode
