package org.firstinspires.ftc.teamcode.OpMode;

import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.PIDFController;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Logical.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Physical.Intake.IntakeMotor;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import java.util.prefs.BackingStoreException;


@Autonomous(name = "Auto Front Red New", group = "Auto")
public class AutoFrontRedNew extends OpMode {
    ShooterSubsystem shooter;
    IntakeMotor intakeMotor;

    private Follower follower;
    private Timer pathTimer;
    private int pathState;
    private final Pose startPose = new Pose(128, 124, Math.toRadians(45));
    private final Pose scorePoseFront = new Pose(96, 85, Math.toRadians(45));
    private final Pose setup1Pose = new Pose(99, 84, Math.toRadians(0));
    private final Pose pickup1Pose = new Pose(132, 84, Math.toRadians(0));


    private Path scorePreload;
    private PathChain setup1, pickup1, shootPickup1, setup1Reverse;

    public void buildPaths(){
        scorePreload = new Path(new BezierLine(startPose, scorePoseFront));
        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), scorePoseFront.getHeading());

        setup1 = follower.pathBuilder()
                .addPath(new BezierLine(scorePoseFront, setup1Pose))
                .setLinearHeadingInterpolation(scorePoseFront.getHeading(),setup1Pose.getHeading())
                .build();

        pickup1 = follower.pathBuilder()
                .addPath(new BezierLine(setup1Pose, pickup1Pose))
                .setConstantHeadingInterpolation(0)
                .build();
        setup1Reverse = follower.pathBuilder()
                .addPath(new BezierLine(pickup1Pose,setup1Pose))
                .setConstantHeadingInterpolation(0)
                .build();

        shootPickup1 = follower.pathBuilder()
                .addPath(new BezierLine(pickup1Pose, scorePoseFront))
                .setLinearHeadingInterpolation(pickup1Pose.getHeading(), scorePoseFront.getHeading())
                .build();
    }


    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0: // Move to score preload
                follower.followPath(scorePreload);
                setPathState(1);
                break;

            case 1:
                if(pathTimer.getElapsedTimeSeconds() > 1){
                    shooter.shootBall(getDistance());
                }
                if(pathTimer.getElapsedTimeSeconds() > 3){
                    intakeMotor.moveMotorForward(1);
                }
                if(!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 4){
                    shooter.setRPM(0);
                    intakeMotor.stopMotor(0);
                }
                if(!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 6){
                    shooter.setRPM(-2000);
                    follower.followPath(setup1);
                    pathTimer.resetTimer();
                    setPathState(2);
                }

                break;
            case 2:
                if(pathTimer.getElapsedTimeSeconds() > 1){
                    intakeMotor.moveMotorForward(0.75);
                }
                if(pathTimer.getElapsedTimeSeconds() > 3) {
                    follower.followPath(pickup1);
                    pathTimer.resetTimer();
                    setPathState(3);
                }

                break;
            case 3:
                intakeMotor.moveMotorForward(0.75);
                if(!follower.isBusy()){
                    follower.followPath(setup1Reverse);
                }
                if(!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 2){
                    follower.followPath(pickup1);
                }
                if(!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 4){
                    setPathState(4);
                }
            case 4:
                if(pathTimer.getElapsedTimeSeconds() > 1){
                    shooter.setRPM(-1000);
                    intakeMotor.stopMotor(0);
                }
                if(!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 3){
                    follower.followPath(shootPickup1);
                    pathTimer.resetTimer();
                    setPathState(4);
                }

                break;
            case 5:
                intakeMotor.moveMotorForward(-0.3);
                shooter.setRPM(-1000);
                if(pathTimer.getElapsedTimeSeconds() > 1){
                    shooter.stopShoot();
                }
                if(pathTimer.getElapsedTimeSeconds() > 3){
                    shooter.shootBall(getDistance() + 5);
                }
                if(pathTimer.getElapsedTimeSeconds() > 6.5) {
                    intakeMotor.moveMotorForward(1);
                }
                if(pathTimer.getElapsedTimeSeconds() > 10){
                    intakeMotor.moveMotorForward(1);
                }
                if(pathTimer.getElapsedTimeSeconds() > 12){
                    shooter.setRPM(0);
                    intakeMotor.stopMotor(0);
//                    follower.followPath(setup1);
                    setPathState(-1);
                }

                break;
        }
    }
    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    private double getDistance() {
        // Ensure these coordinates match your field setup
        double targetX = 132;
        double targetY = 132;
        return Math.hypot(targetX - follower.getPose().getX(), targetY - follower.getPose().getY());
    }

    @Override
    public void init() {
        pathTimer = new Timer();
        follower = Constants.createFollower(hardwareMap);
        shooter = new ShooterSubsystem(telemetry, hardwareMap);
        intakeMotor = new IntakeMotor(telemetry, hardwareMap);

        follower.setStartingPose(startPose);
        buildPaths();
    }

    @Override
    public void loop() {
        follower.update();
        autonomousPathUpdate();

        telemetry.addData("State", pathState);
        telemetry.addData("Pose", follower.getPose().toString());
        telemetry.addData("RPM", shooter.getRPM());
        telemetry.update();
    }

    public void start(){
        setPathState(0);
    }
}
