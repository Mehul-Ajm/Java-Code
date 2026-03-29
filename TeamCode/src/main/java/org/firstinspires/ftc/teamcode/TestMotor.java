package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@TeleOp(name = "Single Motor Test", group = "Test")
public class TestMotor extends LinearOpMode {

    // 1. Declare the motor variable
    private DcMotor motor;
    private DcMotorEx shooter;
    double P = 300;
    double I = 0;
    double D = 0;
    double F = 16;
    double RPM = 0;

    @Override
    public void runOpMode() {

        // 2. Hardware Mapping
        // The string "myMotor" must match the name in your Robot Configuration exactly.
        motor = hardwareMap.get(DcMotor.class, "myMotor");
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");

        // 3. Optional: Set Motor Direction
        // Use REVERSE if the motor spins the wrong way when you push the stick forward.
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        shooter.setDirection(DcMotorSimple.Direction.REVERSE);
        shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooter.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,new PIDFCoefficients(P,I,D,F));
        // 4. Optional: Set Zero Power Behavior
        // BRAKE stops the motor immediately when power is 0. FLOAT lets it coast.
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shooter.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);

        // Wait for the driver to press play
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        // Run until the driver presses stop
        while (opModeIsActive()) {

            // 5. Get Input from Gamepad
            // -gamepad1.left_stick_y is negative because pushing the stick up returns a negative value.

            if(gamepad1.yWasPressed()){
                P+=10;
            } else if (gamepad1.xWasPressed()) {
                P-=5;
            }
            if(gamepad1.bWasPressed()){
                F+=5;
            } else if (gamepad1.aWasPressed()) {
                F-=2.5;
            }
            if(gamepad1.dpad_left){
                RPM = 0;
            }
            if(gamepad1.dpadUpWasPressed()){
                RPM+=200;
            } else if (gamepad1.dpadDownWasPressed()) {
                RPM-=50;
            }

            if(gamepad1.right_trigger>0.1){
                motor.setPower(1);
            } else if (gamepad1.left_trigger>0.1) {
                motor.setPower(-1);
            }
            else{
                motor.setPower(0);
            }

            double ticks = RPMtoTicks(RPM);
            shooter.setVelocity(ticks);
            shooter.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,new PIDFCoefficients(P,I,D,F));

            // Telemetry for debugging
            telemetry.addData("P: ", P);
            telemetry.addData("F:", F);
            telemetry.addData("Target RPM", RPM);
            telemetry.addData("Actual RPM", TickstoRPM(shooter.getVelocity()));
            telemetry.update();
        }
    }

    public double RPMtoTicks(double RPM){
        double ticks = (RPM*28)/60;
        return ticks;
    }

    public double TickstoRPM(double ticks){
        double RPM = (ticks/28)*60;
        return RPM;
    }
}
