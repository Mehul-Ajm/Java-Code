//package org.firstinspires.ftc.teamcode.OpMode;
//
//import com.pedropathing.follower.Follower;
//import com.pedropathing.geometry.BezierLine;
//import com.pedropathing.geometry.Pose;
//import com.pedropathing.paths.Path;
//import com.pedropathing.paths.PathChain;
//import com.pedropathing.util.Timer;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//
//import org.firstinspires.ftc.teamcode.Logical.ShooterSubsystem;
//import org.firstinspires.ftc.teamcode.Physical.Intake.IntakeMotor;
//import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
//
//@Autonomous(name = "Auto Front Red Fixed", group = "Auto")
//public class AutoFrontRed extends OpMode {
//
//    private Follower follower;
//    private Timer pathTimer;
//    private int pathState;
//
//    // Subsystems
//    IntakeMotor intake;
//    ShooterSubsystem shooter;
//
//    // Poses - Standardized (Check if mirroring is actually needed for your field)
//    private final Pose startPose = new Pose(20, 124, Math.toRadians(135));
//    private final Pose scorePoseFront = new Pose(55, 85, Math.toRadians(135));
//    private final Pose setup1 = new Pose(45, 84, Math.toRadians(180));
//    private final Pose pickup1Pose = new Pose(12, 84, Math.toRadians(180));
//    private final Pose setup2 = new Pose(45, 60, Math.toRadians(180));
//    private final Pose pickup2 = new Pose(8, 60, Math.toRadians(180));
//    private final Pose parkPose = new Pose(55, 72, Math.toRadians(90));
//
//    private Path scorePreload;
//    private PathChain setupPickup1, grabPickup1, scorePickup1, setupPickup2, grabPickup2, scorePickup2, park;
//
//    public void buildPaths() {
//        scorePreload = new Path(new BezierLine(startPose, scorePoseFront));
//        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), scorePoseFront.getHeading());
//
//        setupPickup1 = follower.pathBuilder()
//                .addPath(new BezierLine(scorePoseFront, setup1))
//                .setLinearHeadingInterpolation(scorePoseFront.getHeading(), setup1.getHeading())
//                .build();
//
//        grabPickup1 = follower.pathBuilder()
//                .addPath(new BezierLine(setup1, pickup1Pose))
//                .setLinearHeadingInterpolation(setup1.getHeading(), pickup1Pose.getHeading())
//                .build();
//
//        scorePickup1 = follower.pathBuilder()
//                .addPath(new BezierLine(pickup1Pose, scorePoseFront))
//                .setLinearHeadingInterpolation(pickup1Pose.getHeading(), scorePoseFront.getHeading())
//                .build();
//
//        setupPickup2 = follower.pathBuilder()
//                .addPath(new BezierLine(scorePoseFront, setup2))
//                .setLinearHeadingInterpolation(scorePoseFront.getHeading(), setup2.getHeading())
//                .build();
//
//        grabPickup2 = follower.pathBuilder()
//                .addPath(new BezierLine(setup2, pickup2))
//                .setLinearHeadingInterpolation(setup2.getHeading(), pickup2.getHeading())
//                .build();
//
//        scorePickup2 = follower.pathBuilder()
//                .addPath(new BezierLine(pickup2, scorePoseFront))
//                .setLinearHeadingInterpolation(pickup2.getHeading(), scorePoseFront.getHeading())
//                .build();
//
//        park = follower.pathBuilder()
//                .addPath(new BezierLine(scorePoseFront, parkPose))
//                .setLinearHeadingInterpolation(scorePoseFront.getHeading(), parkPose.getHeading())
//                .build();
//    }
//
//    public void autonomousPathUpdate() {
//        switch (pathState) {
//            case 0: // Move to score preload
//                follower.followPath(scorePreload);
//                shooter.shootBall(getDistance());
//                setPathState(1);
//                break;
//
//            case 1: // Scoring Preload
//                if (pathTimer.getElapsedTimeSeconds() > 1.5) {
//                    intake.intakeMotor.setPower(1); // Feed ball to shooter
//                }
//                // Transition after path ends and enough time for shot
//                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 3.0) {
//                    shooter.setRPM(0);
//                    intake.stopMotor(0);
//                    follower.followPath(setupPickup1, true);
//                    setPathState(2);
//                }
//                break;
//
//            case 2: // Move to Setup 1 & Clear Jam
//                if (pathTimer.getElapsedTimeSeconds() > 0.5) {
//                    shooter.setRPM(-500); // Reverse to clear
//                }
//                if (!follower.isBusy()) {
//                    intake.moveMotorForward(1); // Start intake for pickup
//                    follower.followPath(grabPickup1);
//                    setPathState(3);
//                }
//                break;
//
//            case 3: // Grabbing Pickup 1
//                if (!follower.isBusy()) {
//                    follower.followPath(scorePickup1);
//                    setPathState(4);
//                }
//                break;
//
//            case 4: // Scoring Pickup 1
//                if (pathTimer.getElapsedTimeSeconds() < 0.5) {
//                    intake.intakeMotor.setPower(-0.2); // Tiny outtake to seat ball
//                } else if (pathTimer.getElapsedTimeSeconds() > 1.0 && pathTimer.getElapsedTimeSeconds() < 2.5) {
//                    shooter.shootBall(getDistance());
//                } else if (pathTimer.getElapsedTimeSeconds() > 2.5) {
//                    intake.moveMotorForward(1); // Feed
//                }
//
//                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 4.5) {
//                    shooter.setRPM(0);
//                    intake.stopMotor(0);
//                    follower.followPath(setupPickup2, true); // FIXED: Was setupPickup1
//                    setPathState(5);
//                }
//                break;
//
//            case 5: // Move to Setup 2
//                if (pathTimer.getElapsedTimeSeconds() > 0.5) shooter.setRPM(-500);
//                if (!follower.isBusy()) {
//                    intake.moveMotorForward(1);
//                    follower.followPath(grabPickup2);
//                    setPathState(6);
//                }
//                break;
//
//            case 6: // Grabbing Pickup 2
//                if (!follower.isBusy()) {
//                    follower.followPath(scorePickup2);
//                    setPathState(7);
//                }
//                break;
//
//            case 7: // Scoring Pickup 2
//                if (pathTimer.getElapsedTimeSeconds() > 1.5) {
//                    shooter.setRPM(2700);
//                }
//                if (pathTimer.getElapsedTimeSeconds() > 3.5) {
//                    intake.moveMotorForward(1);
//                }
//                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 5.5) {
//                    intake.stopMotor(0);
//                    shooter.setRPM(0);
//                    follower.followPath(park);
//                    setPathState(8);
//                }
//                break;
//
//            case 8: // Parking
//                if (!follower.isBusy()) {
//                    setPathState(-1);
//                }
//                break;
//        }
//    }
//
//    public void setPathState(int pState) {
//        pathState = pState;
//        pathTimer.resetTimer();
//    }
//
//    private double getDistance() {
//        // Ensure these coordinates match your field setup
//        double targetX = 132;
//        double targetY = 132;
//        return Math.hypot(targetX - follower.getPose().getX(), targetY - follower.getPose().getY());
//    }
//
//    @Override
//    public void init() {
//        pathTimer = new Timer();
//        follower = Constants.createFollower(hardwareMap);
//        intake = new IntakeMotor(telemetry, hardwareMap);
//        shooter = new ShooterSubsystem(telemetry, hardwareMap);
//
//        scorePoseFront.mirror();
//        pickup1Pose.mirror();
//        pickup2.mirror();
//        parkPose.mirror();
//        setup2.mirror();
//        setup1.mirror();
//        startPose.mirror();
//
//        follower.setStartingPose(startPose);
//        buildPaths();
//    }
//
//    @Override
//    public void loop() {
//        follower.update();
//        autonomousPathUpdate();
//
//        telemetry.addData("State", pathState);
//        telemetry.addData("Pose", follower.getPose().toString());
//        telemetry.update();
//    }
//
//    @Override
//    public void start() {
//        setPathState(0);
//    }
//}