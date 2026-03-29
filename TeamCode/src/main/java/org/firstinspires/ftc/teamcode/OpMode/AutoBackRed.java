package org.firstinspires.ftc.teamcode.OpMode; // make sure this aligns with class location

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Coordinator.Intake;
import org.firstinspires.ftc.teamcode.Coordinator.Shooter;
import org.firstinspires.ftc.teamcode.Logical.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Physical.Intake.IntakeMotor;
import org.firstinspires.ftc.teamcode.Physical.Shooter.ShooterMotor;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "Auto Back Red", group = "Auto")
public class AutoBackRed extends OpMode {

    private Follower follower;
    private Timer pathTimer, opmodeTimer;

    private int pathState;
    IntakeMotor intake;
    ShooterSubsystem shooter;
//    double shootAngle = Math.atan2(Math.abs(12.0-follower.getPose().getX()), Math.abs(132.0-follower.getPose().getY())) + Math.PI/2;

    private Pose startPose = new Pose(63, 8, Math.toRadians(90)); // Start Pose of our robot.
    private Pose scorePoseBack = new Pose(60, 18, Math.toRadians(72)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.
    private Pose setup1 = new Pose(45,36,Math.toRadians(180));
    private Pose pickup1Pose = new Pose(12,34,Math.toRadians(180));
    private Pose parkPose = new Pose(89,72,Math.toDegrees(90));


    private Path scorePreload;
    private PathChain setupPickup1,grabPickup1,scorePickup1,park;
    public void buildPaths() {
        /* This is our scorePreload path. We are using a BezierLine, which is a straight line. */
        scorePreload = new Path(new BezierLine(startPose, scorePoseBack));
        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), scorePoseBack.getHeading());

    /* Here is an example for Constant Interpolation
    scorePreload.setConstantInterpolation(startPose.getHeading()); */

        /* This is our grabPickup1 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        setupPickup1 = follower.pathBuilder()
                .addPath(new BezierLine(scorePoseBack,setup1))
                .setLinearHeadingInterpolation(scorePoseBack.getHeading(),setup1.getHeading())
                .build();
        grabPickup1 = follower.pathBuilder()
                .addPath(new BezierLine(setup1,pickup1Pose))
                .setTangentHeadingInterpolation()
                .build();
        scorePickup1 = follower.pathBuilder()
                .addPath(new BezierLine(pickup1Pose,scorePoseBack))
                .setLinearHeadingInterpolation(pickup1Pose.getHeading(),scorePoseBack.getHeading())
                .build();
        park = follower.pathBuilder()
                .addPath(new BezierLine(scorePoseBack,parkPose))
                .setLinearHeadingInterpolation(scorePoseBack.getHeading(),parkPose.getHeading())
                .build();

        /* This is our scorePickup1 PathChain. We are using a single path with a BezierLine, which is a straight line. */


    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0: // move forward to position where preload is shoot from.
                follower.followPath(scorePreload);
                ; // Start the motor while moving to save time for preload shoot
                telemetry.addLine("Shooter Running");
                setPathState(1);
                pathTimer.resetTimer();
                break;
            case 1: // Where preload is shoot.

            /* You could check for
            - Follower State: "if(!follower.isBusy()) {}"
            - Time: "if(pathTimer.getElapsedTimeSeconds() > 1) {}"
            - Robot Position: "if(follower.getPose().getX() > 36) {}"
            */
                shooter.shootBall(getDistance());
                if(pathTimer.getElapsedTimeSeconds() > 3){
                    intake.moveMotorForward(1); // Move the preload ball into shooting
                    telemetry.addLine("Intake Running");
                }
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 5) {
                    shooter.setRPM(0);
                    intake.stopMotor(0);
                    telemetry.addLine("Shooter Stop");
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    follower.followPath(setupPickup1,true);
//                     setPathState(-1);
                    setPathState(2);
                    pathTimer.resetTimer();
                }
                break;

            case 2: //
                shooter.setRPM(-100); // reverse to unstuck the ball in the deposit
                telemetry.addLine("Shooter Running");
                if(pathTimer.getElapsedTimeSeconds() > 1){
                    intake.moveMotorForward(0.75); // Start intake motor
                    telemetry.addLine("Intake Running");
                }
                if(!follower.isBusy() && pathTimer.getElapsedTimeSeconds()>3){
                    follower.followPath(grabPickup1);
                    setPathState(3);
                    pathTimer.resetTimer();
                }
                break;

            case 3:
                if(!follower.isBusy()){
                    follower.followPath(scorePickup1);
                    setPathState(4);
                    pathTimer.resetTimer();
                }
                break;

            case 4: // At shooting position after picking first set of balls
                // Reverse
                intake.intakeMotor.setPower(-0.1);
                if(pathTimer.getElapsedTimeSeconds() > 1){
                    shooter.setRPM(0);
                    intake.intakeMotor.setPower(0);
                }
                if(pathTimer.getElapsedTimeSeconds() > 2){
                    shooter.shootBall(getDistance());
                }
                if(pathTimer.getElapsedTimeSeconds() > 5){
                    intake.moveMotorForward(1);
                    telemetry.addLine("Intake Running");
                }
                if(!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 9){
                    telemetry.addLine("Shooter Stop");
                    telemetry.addLine("Intake Stop");
                    intake.stopMotor(0);
                    shooter.setRPM(0);
                    follower.followPath(park);
                    setPathState(5);

                }
                break;
            case 5:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower.isBusy()) {
                    /* Set the state to a Case we won't use or define, so it just stops running an new paths */
                    setPathState(-1);
                }
                break;
        }
    }

    /** These change the states of the paths and actions. It will also reset the timers of the individual switches **/
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
    /** This is the main loop of the OpMode, it will run repeatedly after clicking "Play". **/
    @Override
    public void loop() {


        // These loop the movements of the robot, these must be called continuously in order to work
        follower.update();
        autonomousPathUpdate();

        // Feedback to Driver Hub for debugging
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.addData("Time: ", pathTimer.getElapsedTimeSeconds());
        telemetry.update();
    }

    /** This method is called once at the init of the OpMode. **/
    @Override
    public void init() {
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();


        follower = Constants.createFollower(hardwareMap);
        intake = new IntakeMotor(telemetry, hardwareMap);
        shooter = new ShooterSubsystem(telemetry,hardwareMap);
        buildPaths();
        follower.setStartingPose(startPose);

    }

    /** This method is called continuously after Init while waiting for "play". **/
    @Override
    public void init_loop() {}

    /** This method is called once at the start of the OpMode.
     * It runs all the setup actions, including building paths and starting the path system **/
    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }

    /** We do not use this because everything should automatically disable **/
    @Override
    public void stop() {}
}