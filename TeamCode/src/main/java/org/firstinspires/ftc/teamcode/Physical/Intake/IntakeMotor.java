package org.firstinspires.ftc.teamcode.Physical.Intake;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class IntakeMotor {
    public DcMotor intakeMotor;
    public Telemetry telemetry;

    public IntakeMotor(Telemetry telemetry, HardwareMap hardwareMap){
        intakeMotor = hardwareMap.get(DcMotor.class, "intake");

        this.telemetry = telemetry;
    }

    public void moveMotorForward(double power){
        intakeMotor.setPower(power);
    }

    public void moveMotorBackward(double power){
        intakeMotor.setPower(-power);
    }

    public void stopMotor(double power){
        intakeMotor.setPower(0);
    }
}
