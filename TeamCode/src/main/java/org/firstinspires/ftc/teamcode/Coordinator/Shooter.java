package org.firstinspires.ftc.teamcode.Coordinator;

import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.PIDFController;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Logical.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Logical.TurretSubsystem;

public class Shooter {
    ShooterSubsystem shooterSubsystem;
    TurretSubsystem turretSubsystem;
    Follower follower;
    Telemetry telemetry;
    Gamepad gamepad1;
    double direction;
    double kP = 0.04;
    double kI = 0;
    double kD = 0.0018;
    double kF = 0.000;
    PIDFController turretPID = new PIDFController(new PIDFCoefficients(kP, kI, kD, kF));
    double RPM = 0;


    public Shooter(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Follower follower){
        this.follower = follower;
        this.gamepad1 = gamepad1;
        this.telemetry = telemetry;

        shooterSubsystem = new ShooterSubsystem(telemetry,hardwareMap);
        turretSubsystem = new TurretSubsystem(telemetry,hardwareMap);
        turretPID.setTargetPosition(0);
        turretPID.setCoefficients(new PIDFCoefficients(kP, kI, kD, kF));
    }

    public void updateShooter(boolean r, boolean l,boolean y, boolean joyL, boolean joyR, boolean dpadUp, boolean dpadDown, boolean dpadRight, boolean dpadLeft,boolean gamePad2A, boolean gamePad2Y, boolean gamePad2X, boolean isRed){
        if(r || gamePad2A){
            shooterSubsystem.shootBall(getDistance(isRed));
        } else if (l || gamePad2X) {
            shooterSubsystem.setRPM(-1000);
        } else if (joyL) {
            shooterSubsystem.farShoot();
        } else if (joyR) {
            shooterSubsystem.closeShoot();
        } else if(y || gamePad2Y){
            shooterSubsystem.stopShoot();
        } else if (dpadUp) {
            shooterSubsystem.addP(20);
        } else if (dpadDown) {
            shooterSubsystem.subP(5);
        } else if (dpadRight) {
            shooterSubsystem.addF(2);
        } else if (dpadLeft) {
            shooterSubsystem.subF(0.5);
        }

        telemetry.addData("Distance", getDistance(isRed));
        telemetry.addData("RPM", shooterSubsystem.getRPM());
        shooterSubsystem.getPIDF();
    }

//    public void updateTurret(boolean isRed, boolean xButton){
//        turretPID.updatePosition(turretPID.getTargetPosition());
//
//        int targetX = 0;
//        int targetY = 144;
//        double x = follower.getPose().getX();
//        double y = follower.getPose().getY();
//
//
//        if(isRed){
//            targetX = 144;
//        }
//
//        if(xButton){
//            turretSubsystem.stopMotor();
//        }
//
//
//        if (turretSubsystem.difference(isRed, x,y,targetX,targetY,follower.getHeading()) > 180){
//            direction = -360;
//        }else if(turretSubsystem.difference(isRed, x,y,targetX,targetY,follower.getHeading()) < -180){
//            direction = 360;
//        }else{
//            direction = 0;
//        }
//
//        turretPID.setTargetPosition(turretSubsystem.degreesToTicks(direction + (turretSubsystem.difference(isRed, follower.getPose().getX(),follower.getPose().getY(),targetX,targetY,follower.getHeading()))));
//        turretSubsystem.setTurretPower(turretPID.run());
//        telemetry.addData("target(degrees)", direction + (turretSubsystem.difference(isRed, follower.getPose().getX(),follower.getPose().getY(),targetX,targetY,follower.getHeading())));
//    }

    private double getDistance(boolean isRed){
        double x = follower.getPose().getX();
        double y = follower.getPose().getY();
        double distance;

        if(isRed){
            distance = Math.sqrt(Math.pow(132-x,2) + Math.pow(132-y,2));
        }
        else{
            distance = Math.sqrt(Math.pow(12-x,2) + Math.pow(132-y,2));
        }
        return distance;
    }

    public void resetTurretMotor(){
        turretSubsystem.resetTurretMotor();
        turretSubsystem.setTurretPower(0.1);
    }

    public double getRPM(){
        return shooterSubsystem.getRPM();
    }
}
