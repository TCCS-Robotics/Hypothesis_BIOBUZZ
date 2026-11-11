package org.firstinspires.ftc.teamcode.SharedClasses;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

public class RobotHardware {
    //DcMotorEx has extended capabilities like velocity control

    //Drive Motors     Format  - public DcMotor name; or public DcMotorEx name;
    public DcMotor frontLeftDrive; // 19.2:1 goBILDA - 537.7 Ticks per Revolution at the Output Shaft
    public DcMotor frontRightDrive; // 19.2:1 goBILDA - 537.7 Ticks per Revolution at the Output Shaft
    public DcMotor backLeftDrive; // 19.2:1 goBILDA - 537.7 Ticks per Revolution at the Output Shaft
    public DcMotor backRightDrive; // 19.2:1 goBILDA - 537.7 Ticks per Revolution at the Output Shaft

    //Mechanism Motors
    public DcMotorEx flywheel; // 5000 Series 12VDC Motor (Small 1:1) - 28 Ticks per Revolution at the Output Shaft
    public DcMotor GeckoWheels; // 19.2:1 goBILDA - 537.7 Ticks per Revolution at the Output Shaft
    public DcMotor turret; // 19.2:1 goBILDA - 537.7 Ticks per Revolution at the Output Shaft
    public DcMotor intake; // 19.2:1 goBILDA - 537.7 Ticks per Revolution at the Output Shaft


    //Servos -  Format  - public Servo name; -for position based servo - or public CRServo name; - for Continuous servo
    public CRServo intakeLeft; //Left Intake Wheel
    public CRServo intakeRight; //Right Intake Wheel


    //Sensors   FORMAT public Sensor Type name;
    public GoBildaPinpointDriver odo;
    //public Limelight3A limelight;  //Camera for April Tag Detection

///////////////////////////////////////////////////////////////////////////////////////////////
///                                   STATIC VARIABLES                                      ///
//////////////////////////////////////////////////////////////////////////////////////////////
    public static final double flywheel_TICKS_PER_REV = 28.0;


    //////////////////////////    INITIALIZE ALL HARDWARE    /////////////////////////////
    public void init(HardwareMap hardwareMap) {


    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///                                     HARDWARE MAP                                        ///
    //////////////////////////////////////////////////////////////////////////////////////////////
        // Drive Motors
        frontLeftDrive = hardwareMap.get(DcMotor.class, "motorFL");
        frontRightDrive = hardwareMap.get(DcMotor.class, "motorFR");
        backLeftDrive = hardwareMap.get(DcMotor.class, "motorBL");
        backRightDrive = hardwareMap.get(DcMotor.class, "motorBR");

        // Mechanism Motors
        flywheel = hardwareMap.get(DcMotorEx.class,"flywheel");
        intake = hardwareMap.get(DcMotor.class,"intake");
        GeckoWheels = hardwareMap.get(DcMotor.class,"GW");
        turret = hardwareMap.get(DcMotor.class,"turret");


        // Servos
        intakeLeft = hardwareMap.get(CRServo.class, "intakeLeft");
        intakeRight = hardwareMap.get(CRServo.class, "intakeRight");


        // Sensors
        odo = hardwareMap.get(GoBildaPinpointDriver.class, "odo");
        //limelight = hardwareMap.get(Limelight3A.class, "limelight");

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///                                    CONFIGURATIONS                                       ///
    //////////////////////////////////////////////////////////////////////////////////////////////


        //////////////////////////    Drive Motor Configurations   /////////////////////////////
        // Drive Motor Directions
        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);

        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);

        // Brake Mode Behavior
        frontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        //////////////////////////    Flywheel Configurations   /////////////////////////////
        flywheel.setDirection(DcMotorSimple.Direction.FORWARD);

        //Set Brake Behavior - This allows the flywheel to gradually slow down instead of immediately breaking
        flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // Encoder mode lets the motor controller regulate requested velocity.
        flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // - NEEDED TO USE VELOCITY FEEDBACK
        flywheel.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,
                new PIDFCoefficients(40, 0, 0, 12.5));

        // Sets the flywheel Starting Velocity to 0 so it starts stopped.
        flywheel.setVelocity(0);

        //////////////////////////    Intake Configurations   /////////////////////////////
        intake.setDirection(DcMotorSimple.Direction.FORWARD);

        intakeLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        //initialize power for motor and servos
        intake.setPower(0);

        intakeLeft.setPower(0);
        intakeRight.setPower(0);


        //Set Brake Behavior - This allows the flywheel to gradually slow down instead of immediately breaking
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);




        ///////////////////////    Gecko Wheel Lift Configurations   /////////////////////////
        GeckoWheels.setDirection(DcMotorSimple.Direction.FORWARD);



        //////////////////////////    Turret Configurations   /////////////////////////////
        turret.setDirection(DcMotorSimple.Direction.FORWARD);

        //initialize power for motor and servos
        turret.setPower(0);

        //Set Brake Behavior - This allows the flywheel to gradually slow down instead of immediately breaking
        turret.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



        //////////////////    Shooter Pollen/Nectar Configurations   //////////////////////





        // Reset Odometry
        odo.resetPosAndIMU();


    }//Closes Initialization


}// Closes Public Class
