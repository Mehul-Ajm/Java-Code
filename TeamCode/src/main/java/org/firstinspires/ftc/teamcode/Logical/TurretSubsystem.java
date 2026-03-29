package org.firstinspires.ftc.teamcode.Logical;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.PIDFController;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Physical.Shooter.TurretMotor;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class TurretSubsystem {
    TurretMotor turretMotor;
    Telemetry telemetry;

    double headingDisplacement;

    public TurretSubsystem(Telemetry telemetry, HardwareMap hardwareMap){
        turretMotor = new TurretMotor(telemetry, hardwareMap);

        this.telemetry = telemetry;
    }

    public double difference(boolean isRed,double x, double y, double targetX, double targetY, double heading){
        headingDisplacement = 0 - Math.toDegrees(heading); //0 is turret starting displacement
        double difference;

        if(isRed){
            int redtargetAngle = turretMotor.angleToPointDegrees(x, y, targetX, targetY);
            difference = redtargetAngle + headingDisplacement;
        } else {
            int bluetargetAngle = turretMotor.angleToPointDegrees(x, y, targetX, targetY);
            difference = bluetargetAngle + headingDisplacement;
        }
        return difference;
    }

    public double degreesToTicks(double degrees){
        return turretMotor.degreesToTicks(degrees);
    }

    public void setTurretPower(double power){
        turretMotor.setTurretPower(power);
    }

    public void stopMotor(){
        turretMotor.stopMotor();
    }

    public void resetTurretMotor(){
        turretMotor.resetMotor();
    }
}
