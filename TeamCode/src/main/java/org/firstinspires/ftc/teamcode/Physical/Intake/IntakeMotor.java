package org.firstinspires.ftc.teamcode.Physical.Intake;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class IntakeMotor {
    DcMotor intakeMotor;
    Telemetry telemetry;
    double power = 1.0;

    public IntakeMotor(Telemetry telemetry, HardwareMap hardwareMap){
        intakeMotor = hardwareMap.get(DcMotor.class, "intake");

        this.telemetry = telemetry;
    }

    public void moveMotorForward(){
        intakeMotor.setPower(power);
    }

    public void moveMotorBackward(){
        intakeMotor.setPower(-power);
    }

    public void stopMotor(){
        intakeMotor.setPower(0);
    }
}
