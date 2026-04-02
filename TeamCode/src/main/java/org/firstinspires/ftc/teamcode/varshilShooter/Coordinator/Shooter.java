package org.firstinspires.ftc.teamcode.varshilShooter.Coordinator;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.varshilShooter.Logical.varshilShooter;

public class Shooter {
    varshilShooter shooter;
    Telemetry telemetry;
    public Shooter(HardwareMap hardwareMap, Telemetry telemetry){
        shooter = new varshilShooter(hardwareMap,telemetry);
        this.telemetry = telemetry;
    }

    public void update(boolean r, boolean l, boolean y, boolean x, boolean b, boolean a){
        if(r){
            shooter.addRPM(100);
        }
        else if(l){
            shooter.subRPM(50);
        }
        else if(y) {
            shooter.setRPM(-250);
        }
        else if(a){
            shooter.setRPM(0);
        }
        else if(x) {
            shooter.closeShoot();
        }
        else if(b){
            shooter.farShoot();
        }

        telemetry.addData("RPM: ", shooter.getRPM());
    }

    public void addRPM(double addRPM){
        shooter.addRPM(addRPM);
    }

    public void subRPM(double subRPM){
        shooter.subRPM(subRPM);
    }

    public void setRPM(double RPM){
        shooter.setRPM(RPM);
    }

    public void closeShoot(){
        shooter.closeShoot();
    }

    public void farShoot(){
        shooter.farShoot();
    }
}
