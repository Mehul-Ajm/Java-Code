package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@TeleOp(name = "Single Motor Test", group = "Test")
public class MotorTest extends LinearOpMode {

    // 1. Declare the motor variable
    private DcMotor Turretmotor;
    @Override
    public void runOpMode() {

        // 2. Hardware Mapping
        // The string "myMotor" must match the name in your Robot Configuration exactly.
        Turretmotor = hardwareMap.get(DcMotor.class, "myMotor");

        // 3. Optional: Set Motor Direction
        // Use REVERSE if the motor spins the wrong way when you push the stick forward.
        Turretmotor.setDirection(DcMotorSimple.Direction.FORWARD);
        // 4. Optional: Set Zero Power Behavior
        // BRAKE stops the motor immediately when power is 0. FLOAT lets it coast.
        Turretmotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // Wait for the driver to press play
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        // Run until the driver presses stop
        while (opModeIsActive()) {
            if (gamepad1.right_trigger > 0.1) {
                Turretmotor.setPower(gamepad1.right_trigger);
            } else if (gamepad1.left_trigger > 0.1) {
                Turretmotor.setPower(-gamepad1.left_trigger);
            }
            else {
                Turretmotor.setPower(0);
            }
        }
    }
}
