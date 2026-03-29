package org.firstinspires.ftc.teamcode.Logical;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Physical.Shooter.ShooterMotor;

import java.util.logging.Handler;

public class ShooterSubsystem {
    ShooterMotor shooterMotor;
    Telemetry telemetry;
    Follower follower;
    public ShooterSubsystem(Telemetry telemetry, HardwareMap hardwareMap) {
        shooterMotor = new ShooterMotor(telemetry, hardwareMap);

        this.telemetry = telemetry;
    }

    public void stopShoot(){
        shooterMotor.setRPM(0);
    }

    public void setRPM(double RPM){
        shooterMotor.setRPM(RPM);
    }

    public void shootBall(double distance){
        shooterMotor.setRPM(calculateRPM(distance));
    }

    public void closeShoot(){
        shooterMotor.setRPM(2700);
    }

    public void farShoot(){
        shooterMotor.setRPM(3000);
    }

    private double calculateRPM(double distance){
        double x = 8.16267;
        double y = 1960.69412;
        return x*distance + y;
    }

    public void addRPM(double addRPM){
        shooterMotor.setRPM(shooterMotor.getRPM() + addRPM);
    }

    public void subRPM(double subRPM){
        shooterMotor.setRPM(shooterMotor.getRPM() - subRPM);
    }

    public double getRPM(){
        return shooterMotor.getRPM();
    }

    public void addP(double p){
        shooterMotor.addP(p);
    }

    public void subP(double p){
        shooterMotor.subP(p);
    }

    public void addF(double f){
        shooterMotor.addF(f);
    }

    public void subF(double f){
        shooterMotor.subF(f);
    }

    public void getPIDF(){
        shooterMotor.getPIDF();
    }
}
