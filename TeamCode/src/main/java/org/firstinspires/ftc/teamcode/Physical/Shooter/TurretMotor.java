package org.firstinspires.ftc.teamcode.Physical.Shooter;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TurretMotor {
    DcMotor turretMotor;
    Telemetry telemetry;

    public TurretMotor(Telemetry telemetry, HardwareMap hardwareMap){
        turretMotor = hardwareMap.get(DcMotor.class, "turret");

        this.telemetry = telemetry;
    }

    public void moveMotorRight(double power){
        turretMotor.setPower(power);
    }

    public void moveMotorLeft(double power){
        turretMotor.setPower(-power);
    }

    public void stopMotor(){
        turretMotor.setPower(0);
    }


}
