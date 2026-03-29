package org.firstinspires.ftc.teamcode.Physical.Shooter;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Mat;

public class ShooterMotor {
    private DcMotorImplEx shooter;
    double P = 400;
    double I = 0;
    double D = 0;
    double F = 16.5;

    Telemetry telemetry;
    public ShooterMotor(Telemetry telemetry, HardwareMap hardwareMap) {
        this.telemetry = telemetry;
        shooter = hardwareMap.get(DcMotorImplEx.class, "shooter");

        shooter.setDirection(DcMotorSimple.Direction.REVERSE);
        shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooter.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,new PIDFCoefficients(P,I,D,F));
    }

    private double RPMtoTicks(double RPM){
        double ticks = (RPM*28)/60;
        return ticks;
    }

    private double TickstoRPM(double ticks){
        double RPM = (ticks/28)*60;
        return RPM;
    }

    public void setRPM(double RPM){
        double ticks = RPMtoTicks(RPM);
        shooter.setVelocity(ticks);
    }

    public double getRPM() {
        return TickstoRPM(shooter.getVelocity());
    }

    public void addP(double p){
        P += p;
    }

    public void subP(double p){
        P -= p;
    }

    public void addF(double f){
        F+=f;
    }

    public void subF(double f){
        F -= f;
    }

    public void getPIDF(){
        telemetry.addData("P: ", P);
        telemetry.addData("I: ", I);
        telemetry.addData("D: ", D);
        telemetry.addData("F: ", F);
    }
}
