package org.firstinspires.ftc.teamcode.Coordinator;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Logical.IntakeSubsystem;

public class Intake {
    IntakeSubsystem intakeSubsystem;
    Telemetry telemetry;
    Gamepad gamepad1;

    public Intake(Telemetry telemetry, HardwareMap hardwareMap,Gamepad gamepad1){
        intakeSubsystem = new IntakeSubsystem(telemetry,hardwareMap);

        this.gamepad1 = gamepad1;
        this.telemetry = telemetry;
    }

    public void update(){
        if(gamepad1.right_trigger>0.01){
            intakeSubsystem.moveIntakeForward();
        }
        else if (gamepad1.left_trigger>0.01){
            intakeSubsystem.moveIntakeBackward();
        }
        else{
            intakeSubsystem.stopIntake();
        }
    }
}
