package org.firstinspires.ftc.teamcode.OpMode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Coordinator.Drivetrain;
import org.firstinspires.ftc.teamcode.Coordinator.Intake;
import org.firstinspires.ftc.teamcode.varshilShooter.Coordinator.Shooter;

@TeleOp(name = "varshilShooter")
public class TeleOPVarshil extends OpMode {
    Drivetrain drivetrain;
    Intake intake;
    Shooter shooter;

    @Override
    public void init() {
        drivetrain = new Drivetrain(telemetry, hardwareMap, gamepad1);
        intake = new Intake(telemetry, hardwareMap, gamepad1);
        shooter = new Shooter(hardwareMap, telemetry);
    }

    @Override
    public void loop() {
        drivetrain.update(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        intake.update(gamepad1.right_trigger, gamepad1.left_trigger);
        shooter.update(gamepad1.right_bumper, gamepad1.left_bumper,gamepad1.yWasPressed(),gamepad1.xWasPressed(),gamepad1.bWasPressed(),gamepad1.aWasPressed());
    }
}
