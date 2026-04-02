package org.firstinspires.ftc.teamcode.varshilShooter.Logical;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Physical.Shooter.ShooterMotor;

public class varshilShooter {
    ShooterMotor shooterMotor;
    Telemetry telemetry;
    public varshilShooter(HardwareMap hardwareMap, Telemetry telemetry){
        shooterMotor = hardwareMap.get(ShooterMotor.class, "shooter");
        this.telemetry = telemetry;
    }

    public void setRPM(double RPM){
        shooterMotor.setRPM(RPM);
    }

    public void addRPM(double addRPM){
        shooterMotor.setRPM(shooterMotor.getRPM() + addRPM);
    }

    public void subRPM(double subRPM){
        shooterMotor.setRPM(shooterMotor.getRPM() - subRPM);
    }

    public void closeShoot(){
        shooterMotor.setRPM(2700);
    }

    public void farShoot(){
        shooterMotor.setRPM(3000);
    }

    public double getRPM(){
        return shooterMotor.getRPM();
    }

    public void stopShoot(){
        shooterMotor.setRPM(0);
    }
}
