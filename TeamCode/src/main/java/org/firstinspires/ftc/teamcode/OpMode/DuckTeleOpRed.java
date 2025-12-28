package org.firstinspires.ftc.teamcode.OpMode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Coordinator.Drivetrain;
import org.firstinspires.ftc.teamcode.Coordinator.Intake;
import org.firstinspires.ftc.teamcode.Coordinator.Shooter;

@TeleOp(name = "DuckTeleOpRed", group = "Linear OpMode")
public class DuckTeleOpRed extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Drivetrain drivetrain = new Drivetrain(telemetry,hardwareMap,gamepad1);
        Intake intake = new Intake(telemetry,hardwareMap,gamepad1);
        Shooter shooter = new Shooter(telemetry,hardwareMap,gamepad1);


        waitForStart();
        while (opModeIsActive()) {
            drivetrain.update();
            intake.update();
            shooter.update(true);


            //Telemetry Update
            telemetry.update();
        }
    }
}
