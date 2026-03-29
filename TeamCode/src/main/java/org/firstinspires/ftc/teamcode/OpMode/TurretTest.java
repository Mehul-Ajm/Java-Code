package org.firstinspires.ftc.teamcode.OpMode;

import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.PIDFController;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.teamcode.Coordinator.Drivetrain;
import org.firstinspires.ftc.teamcode.Coordinator.Intake;
import org.firstinspires.ftc.teamcode.Coordinator.Shooter;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
@TeleOp(name = "PID Turret Test")
public class TurretTest extends OpMode {
    private final Pose startPose = new Pose(72, 72, Math.toRadians(90)); // Start Pose of our robot.
    private final Pose cornerPose = new Pose(6,9, Math.toRadians(90));
    DcMotorImplEx motorEx;
    //    MotorPhys motor;
    double kP = 0.09 ;  //
    //TODO: need to tune D and also positive ticks rotates turret counter clockwise or increasing angle negative goes other way
    //TODO: 0 ticks = 0 degrees and 392 ticks = 180 degrees --> do the calc
    double kI = 0;
    double kD = 0.0018;
    double kF = 0.000;

    double manualOffSet;

    PIDFController turretPID = new PIDFController(new PIDFCoefficients(kP, kI, kD, kF));

    double direction = 1;

    boolean lastRB = false;
    boolean lastLB = false;
    boolean lastRT = false;
    boolean lastLT = false;

    boolean red = true;
    double difference;

    double headingDisplacement;

    private Follower follower;

    static final double TICKS_PER_180_DEG = 392;
    static final double DEGREES_PER_180_TICKS = 180.0;

    static final double TICKS_PER_DEGREE = TICKS_PER_180_DEG / DEGREES_PER_180_TICKS;
    static final double DEGREES_PER_TICK = DEGREES_PER_180_TICKS / TICKS_PER_180_DEG;
    Shooter shooter;
    Drivetrain drivetrain;
    Intake intake;
    double targetBlue;
    double targetRed;

    public int degreesToTicks(double degrees) {
        return (int) Math.round(degrees * TICKS_PER_DEGREE);
    }

    public double getTurretDegrees() {
        return motorEx.getCurrentPosition() * DEGREES_PER_TICK;
    }

    @Override
    public void init(){
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);

        motorEx = hardwareMap.get(DcMotorImplEx.class, "turret");


        // IMPORTANT: Reset the encoder first
        motorEx.setMode(DcMotorImplEx.RunMode.STOP_AND_RESET_ENCODER);
        motorEx.setZeroPowerBehavior(DcMotorImplEx.ZeroPowerBehavior.FLOAT);
        motorEx.setDirection(DcMotorSimple.Direction.REVERSE);
        motorEx.setMode(DcMotorImplEx.RunMode.RUN_WITHOUT_ENCODER);

        drivetrain = new Drivetrain(telemetry, hardwareMap, gamepad1);
        intake = new Intake(telemetry, hardwareMap, gamepad1);
        shooter = new Shooter(telemetry, hardwareMap, gamepad1, follower);

        // Then run using encoder
//        motor.setMode(DcMotorImplEx.RunMode.RUN_USING_ENCODER);
        //motorEx.setTargetPosition(0);
        turretPID.setTargetPosition(0);
        //motorEx.setMode(DcMotorImplEx.RunMode.RUN_TO_POSITION);
//        motorEx.setPIDFCoefficients(
//                DcMotorImplEx.RunMode.RUN_TO_POSITION,
//                new PIDFCoefficients(kP, kI, kD, kF)
//        );

        // motorEx.setPositionPIDFCoefficients(kP);
        //motorEx.setPower(0.5); // REQUIRED
    }

    @Override
    public void loop(){
        turretPID.setCoefficients(new PIDFCoefficients(kP, kI, kD, kF));
        follower.update();

        int bluetargetAngle = angleToPointDegrees(follower.getPose().getX(), follower.getPose().getY(), 0, 144,follower.getHeading());
        int redtargetAngle = angleToPointDegrees(follower.getPose().getX(), follower.getPose().getY(), 144, 144, follower.getHeading());

        headingDisplacement = 0 - Math.toDegrees(follower.getHeading()); //0 is turret starting displacement

        if(red){
            difference = redtargetAngle + headingDisplacement;
        }else{
            difference = bluetargetAngle + headingDisplacement;
        }


        if (difference > 180){
            direction = -360;
        }else if(difference < -180){
            direction = 360;
        }else{
            direction = 0;
        }

        targetBlue = degreesToTicks(direction + (bluetargetAngle + headingDisplacement));
        targetRed = degreesToTicks(direction + (redtargetAngle + headingDisplacement));


        if(gamepad1.a){
            red = false;
            //motorEx.setTargetPosition(degreesToTicks(direction + (bluetargetAngle + headingDisplacement)));
        }
        else if(gamepad1.y){
            red = true;
            //motorEx.setTargetPosition(degreesToTicks(direction + (redtargetAngle + headingDisplacement)));
        }
        else if (gamepad1.b){
            //motorEx.setTargetPosition(0);
            turretPID.setTargetPosition(0);
        }

        if(red && !gamepad1.b){
            turretPID.setTargetPosition(targetRed + manualOffSet);
        } else if (!red && !gamepad1.b) {
            turretPID.setTargetPosition(targetBlue + manualOffSet);
        }

        if (gamepad1.dpad_right || gamepad2.dpad_right) {
//            turretPID.setTargetPosition(turretPID.getTargetPosition() + degreesToTicks(2));
            manualOffSet +=4;
        }
        if (gamepad1.dpad_left || gamepad2.dpad_left) {
//            turretPID.setTargetPosition(turretPID.getTargetPosition() - degreesToTicks(2));
            manualOffSet -=4;
        }
        if(gamepad1.xWasPressed()){
            follower.setPose(cornerPose);
        }

        if(gamepad2.right_bumper){
            manualOffSet += 10;
        }
        if(gamepad2.left_bumper){
            manualOffSet -= 10;
        }
//        if(gamepad2.a){
//
//        }

// ----- kD -----
        if (gamepad2.dpadUpWasPressed()) {
            kD = kD + 0.00005;
        }
        if (gamepad2.dpadDownWasPressed()) {
            kD = kD - 0.00005;
        }

        if (gamepad2.dpadRightWasPressed()) {
            kP = kP + 0.005;
        }
        if (gamepad2.dpadLeftWasPressed()) {
            kP = kP - 0.005;
        }

        lastRB = gamepad1.right_bumper ;
        lastLB = gamepad1.left_bumper;
        lastRT = gamepad1.right_trigger > 0.5;
        lastLT = gamepad1.left_trigger > 0.5;
        //motorEx.setPositionPIDFCoefficients(kP);
        turretPID.updatePosition(motorEx.getCurrentPosition());
        double maxTurretPower = 0.4; // Limit to 40% speed
        double pidOutput = turretPID.run();
        double finalPower = Math.max(-maxTurretPower, Math.min(maxTurretPower, pidOutput));
        if(shooter.getRPM() > 0){
            motorEx.setPower(finalPower);
        }else {
            motorEx.setPower(0);
        }

        intake.update(gamepad1.right_trigger, gamepad1.left_trigger);
        shooter.updateShooter(gamepad1.right_bumper, gamepad1.left_bumper,gamepad1.yWasPressed(),gamepad1.left_stick_button,gamepad1.right_stick_button, gamepad1.dpadUpWasPressed(), gamepad1.dpadDownWasPressed(), gamepad1.dpadRightWasPressed(), gamepad1.dpadLeftWasPressed(),gamepad2.a, gamepad2.y, gamepad2.x,red);
//        shooter_turret.updateTurret(false, gamepad1.xWasPressed());
        drivetrain.update(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

        telemetry.addData("DegreeTarget", getTurretDegrees());
        telemetry.addData("EncoderPos", motorEx.getCurrentPosition());
        telemetry.addData("Heading Displacement", headingDisplacement);
        telemetry.addData("Turret Heading", getTurretDegrees());
        telemetry.addData("Power", turretPID.run());
        telemetry.addData("Target", turretPID.getTargetPosition());
        telemetry.addData("KP", turretPID.getCoefficients().P);
        telemetry.addData("KD", turretPID.getCoefficients().D);
        telemetry.addData("Error", turretPID.getError());

        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", Math.toDegrees(follower.getPose().getHeading()));
        telemetry.addData("Difference", difference);
        telemetry.addData("Manual Offset", manualOffSet);
        telemetry.update();
    }

    public int angleToPointDegrees(double curX, double curY, double targetPointX, double targetPointY, double botHeading){
        curX = curX - 2.5 * Math.cos(Math.toRadians(botHeading));
        curY = curY - 2.5 * Math.sin(Math.toRadians(botHeading));

        double xDifference = (targetPointX - curX);
        double yDifference = (targetPointY - curY); // Difference between the two points
        //angle formula: arctangent(yDiff / xDiff)
        double angleRad = (Math.atan2(yDifference, xDifference));
        //since arctangent gives back in radians, not degrees
        int angleDeg = (int)(Math.toDegrees(angleRad));
        //Integer because doesn't really get the exact heading due to sensor or human error
        return angleDeg;
    }
}
