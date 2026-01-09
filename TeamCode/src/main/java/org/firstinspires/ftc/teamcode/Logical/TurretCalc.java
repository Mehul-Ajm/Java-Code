package org.firstinspires.ftc.teamcode.Logical;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Physical.Shooter.TurretMotor;

public class TurretCalc {
    double P;
    double I;
    double D;
    TurretMotor turretMotor;
    Telemetry telemetry;

    double targetHeading;
    double currentHeading;

    public TurretCalc(double P, double I, double D, Telemetry telemetry, HardwareMap hardwareMap){
        this.P = P;
        this.I = I;
        this.D = D;

        turretMotor = new TurretMotor(telemetry, hardwareMap);
        this.telemetry = telemetry;
    }
}
