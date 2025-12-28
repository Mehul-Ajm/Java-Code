package org.firstinspires.ftc.teamcode.Physical.Shooter;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TurretMotor {
    DcMotor turretMotor;
    Telemetry telemetry;
    double power = 1;

    public TurretMotor(Telemetry telemetry, HardwareMap hardwareMap){
        turretMotor = hardwareMap.get(DcMotor.class, "turret");

        this.telemetry = telemetry;
    }

    public void moveMotorRight(){
        turretMotor.setPower(power);
    }

    public void moveMotorLeft(){
        turretMotor.setPower(-power);
    }

    public void stopMotor(){
        turretMotor.setPower(0);
    }


}
