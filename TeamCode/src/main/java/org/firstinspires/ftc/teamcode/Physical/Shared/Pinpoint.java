package org.firstinspires.ftc.teamcode.Physical.Shared;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Pinpoint {
    GoBildaPinpointDriver odo;
    Telemetry telemetry;

    public Pinpoint(Telemetry telemetry, HardwareMap hardwareMap){
        odo = hardwareMap.get(GoBildaPinpointDriver.class, "odo");

        this.telemetry = telemetry;
    }

    public double getX(){
        return odo.getPosX(DistanceUnit.INCH);
    }

    public double getY(){
        return odo.getPosY(DistanceUnit.INCH);
    }

    public double getHeading(){
        return odo.getHeading(AngleUnit.DEGREES);
    }
}
