package org.firstinspires.ftc.teamcode.Logical;

import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Physical.Shared.Pinpoint;
import org.firstinspires.ftc.teamcode.Physical.Shooter.MotorController;

public class ShooterSubsystem {
    DcMotorImplEx shooterMotor;
    Servo angle;
    MotorController motorController;
    Telemetry telemetry;
    Pinpoint odo;

    public ShooterSubsystem(Telemetry telemetry, HardwareMap hardwareMap){
        shooterMotor = hardwareMap.get(DcMotorImplEx.class, "shooter");
        motorController = new MotorController(shooterMotor);
        odo = new Pinpoint(telemetry, hardwareMap);

        this.telemetry = telemetry;
    }

    public void shoot(boolean isRed){
        double distanceToGoal = ShooterCalc.distance(odo.getX(),odo.getY(),isRed);
        double v0 = ShooterCalc.v0(distanceToGoal);
        double angleDeg = ShooterCalc.angle(distanceToGoal);
        angle.setPosition(ShooterCalc.angleToServo(angleDeg));
        motorController.setRPM(ShooterCalc.velocityToRPM(v0));
    }

    public void humanPlayer(){
        motorController.max(true);
    }

    public void stopMotor(){
        motorController.stop();
    }

    public void moveTurret(boolean isLeft){
        double targetHeading = 0;
        if(isLeft){
            targetHeading = 15;
        }
    }
}
