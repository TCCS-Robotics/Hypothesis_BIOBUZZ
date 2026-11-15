
package org.firstinspires.ftc.teamcode.TESTING;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Servo Position Test", group = "Testing")
public class Servo_Calibration extends LinearOpMode {

    // ==============================
    // SERVO SETTINGS
    // ==============================

    private Servo servo1;
    private Servo servo2;

    private double servo1Position = 0.3;
    private double servo2Position = 0.3;

    private static final double STEP = 0.01;

    // Set true if testing two servos
    private boolean useServo2 = true;

    // Direction settings
    private boolean servo1Reversed = false;
    private boolean servo2Reversed = true;

    // ==============================
    // BUTTON TRACKING
    // ==============================

    // Prevents holding a button from
    // repeatedly changing the position

    private boolean lastUp = false;
    private boolean lastDown = false;
    private boolean lastLeft = false;
    private boolean lastRight = false;

    private boolean lastX = false;
    private boolean lastB = false;
    private boolean lastA = false;

    @Override
    public void runOpMode() {

        // ==============================
        // HARDWARE INITIALIZATION
        // ==============================

        servo1 = hardwareMap.get(Servo.class, "SL");

        // Servo 2 is optional
        servo2 = hardwareMap.tryGet(Servo.class, "SR");

        if (servo2 == null) {
            useServo2 = false;
        }

        // ==============================
        // INITIALIZATION LOOP
        // ==============================

        while (opModeInInit()) {

            // Toggle Servo 1 direction
            if (gamepad1.x && !lastX) {
                servo1Reversed = !servo1Reversed;
            }

            // Toggle Servo 2 direction
            if (gamepad1.b && !lastB && servo2 != null) {
                servo2Reversed = !servo2Reversed;
            }

            // Enable / disable Servo 2
            if (gamepad1.a && !lastA && servo2 != null) {
                useServo2 = !useServo2;
            }

            lastX = gamepad1.x;
            lastB = gamepad1.b;
            lastA = gamepad1.a;

            // Display initialization settings
            telemetry.addLine("=== SERVO INITIALIZATION ===");

            telemetry.addData("Servo 1 Direction",
                    servo1Reversed ? "REVERSE" : "FORWARD");

            telemetry.addData("Servo 2 Enabled", useServo2);

            telemetry.addData("Servo 2 Direction",
                    servo2Reversed ? "REVERSE" : "FORWARD");

            telemetry.addLine();
            telemetry.addLine("X = Toggle Servo 1 Direction");
            telemetry.addLine("B = Toggle Servo 2 Direction");
            telemetry.addLine("A = Enable/Disable Servo 2");
            telemetry.addLine();
            telemetry.addLine("Press START when ready");

            telemetry.update();

            sleep(30);
        }

        if (isStopRequested()) return;

        // ==============================
        // APPLY SERVO DIRECTIONS
        // ==============================

        servo1.setDirection(
                servo1Reversed
                        ? Servo.Direction.REVERSE
                        : Servo.Direction.FORWARD
        );

        if (servo2 != null && useServo2) {
            servo2.setDirection(
                    servo2Reversed
                            ? Servo.Direction.REVERSE
                            : Servo.Direction.FORWARD
            );
        }

        // ==============================
        // SET STARTING POSITIONS
        // ==============================

        servo1.setPosition(servo1Position);

        if (servo2 != null && useServo2) {
            servo2.setPosition(servo2Position);
        }

        // ==============================
        // MAIN TEST LOOP
        // ==============================

        while (opModeIsActive()) {

            // --------------------------
            // SERVO 1 CONTROLS
            // --------------------------

            // D-pad UP increases position
            if (gamepad1.dpad_up && !lastUp) {
                servo1Position += STEP;
            }

            // D-pad DOWN decreases position
            if (gamepad1.dpad_down && !lastDown) {
                servo1Position -= STEP;
            }

            // Keep position between 0 and 1
            servo1Position = Range.clip(
                    servo1Position, 0.0, 1.0
            );

            servo1.setPosition(servo1Position);

            // --------------------------
            // SERVO 2 CONTROLS
            // --------------------------

            if (servo2 != null && useServo2) {

                // D-pad Up increases position
                if (gamepad1.dpad_up && !lastUp) {
                    servo2Position += STEP;
                }

                // D-pad Down decreases position
                if (gamepad1.dpad_down && !lastDown) {
                    servo2Position -= STEP;
                }

                servo2Position = Range.clip(
                        servo2Position, 0.0, 1.0
                );

                servo2.setPosition(servo2Position);
            }

            // --------------------------
            // UPDATE BUTTON STATES
            // --------------------------

            lastUp = gamepad1.dpad_up;
            lastDown = gamepad1.dpad_down;
            lastLeft = gamepad1.dpad_left;
            lastRight = gamepad1.dpad_right;

            // --------------------------
            // TELEMETRY
            // --------------------------

            telemetry.addLine("=== SERVO POSITION TEST ===");
            telemetry.addLine();

            telemetry.addData("Servo 1 Position",
                    "%.2f", servo1Position);

            telemetry.addData("Servo 1 Direction",
                    servo1Reversed ? "REVERSE" : "FORWARD");

            telemetry.addLine();

            if (servo2 != null && useServo2) {

                telemetry.addData("Servo 2 Position",
                        "%.2f", servo2Position);

                telemetry.addData("Servo 2 Direction",
                        servo2Reversed ? "REVERSE" : "FORWARD");

            } else {
                telemetry.addLine("Servo 2: DISABLED");
            }

            telemetry.addLine();
            telemetry.addLine("UP/DOWN = Servo 1 +/- 0.05");
            telemetry.addLine("RIGHT/LEFT = Servo 2 +/- 0.05");

            telemetry.update();

            ;
        }
    }
}
