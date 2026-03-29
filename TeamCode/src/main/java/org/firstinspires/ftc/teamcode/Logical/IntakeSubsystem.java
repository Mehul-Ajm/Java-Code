package org.firstinspires.ftc.teamcode.Logical;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Physical.Intake.IntakeMotor;

public class IntakeSubsystem {
    IntakeMotor intakeMotor;
    Telemetry telemetry;

    public IntakeSubsystem(Telemetry telemetry, HardwareMap hardwareMap){
        intakeMotor = new IntakeMotor(telemetry,hardwareMap);

        this.telemetry = telemetry;
    }

    public void moveIntakeForward(double power){
        intakeMotor.moveMotorForward(power);
    }

    public void moveIntakeBackward(double power){
        intakeMotor.moveMotorBackward(power);
    }

    public void stopIntake(){
        intakeMotor.stopMotor(0);
    }
}
