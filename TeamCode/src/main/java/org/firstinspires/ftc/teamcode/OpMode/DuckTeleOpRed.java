//package org.firstinspires.ftc.teamcode.OpMode;
//
//import com.pedropathing.control.PIDFCoefficients;
//import com.pedropathing.control.PIDFController;
//import com.pedropathing.follower.Follower;
//import com.pedropathing.geometry.Pose;
//import com.pedropathing.util.Timer;
//import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.util.Range;
//
////import org.firstinspires.ftc.teamcode.Coordinator.Drivetrain;
//import org.firstinspires.ftc.teamcode.Coordinator.Intake;
//import org.firstinspires.ftc.teamcode.Coordinator.Shooter;
//import org.firstinspires.ftc.teamcode.Physical.Shooter.ShooterMotor;
//import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
//
//@TeleOp(name = "DuckTeleOpRed", group = "Linear OpMode")
//public class DuckTeleOpRed extends LinearOpMode {
//
//    Follower follower;
//    Pose startingPose = new Pose(89 ,72, Math.toRadians(90));
//    boolean AutoRun;
//    GoBildaPinpointDriver pinpoint;
//
//    double headingSetPoint = 0;
//    double kP = -0.05;
//    double kI = 0;
//    double kF = 0;
//    double kD = 0.0001;
//
//
//    double startRPM;
//    double error = 0;
//    double redHeading;
//    double blueHeading;
//    double xBlue;
//    double xRed;
//    double y;
//    double distanceToGoalBlue;
//    double distanceToGoalRed;
//    ShooterMotor shooterMotor;
//    double getX;
//    double getY;
//    PIDFController alignPID;
//    Timer timer = new Timer();
//    final double UPDATE_MS = 30;
//    long lastUpdateTime;
//
//
//
//
//    //    public boolean isAutoRun(double headingSetPoint){
////
////        if(a){
////            headingSetPoint = 45;
////            return AutoRun = true;
////        } else if (b) {
////            headingSetPoint = 270;
////            return AutoRun = true;
////        }
////        else{
////            return AutoRun = false;
////        }
////    }
////
//    @Override
//    public void runOpMode() throws InterruptedException {
//        Intake intake = new Intake(telemetry,hardwareMap,gamepad1);
//        Shooter shooter = new Shooter(telemetry,hardwareMap,gamepad1);
//
//        alignPID = new PIDFController(new PIDFCoefficients(kP, kI, kD, kF));
//        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
//
//        shooterMotor = new ShooterMotor(telemetry, hardwareMap);
//
//        follower = Constants.createFollower(hardwareMap);
//        follower.setStartingPose(startingPose);
//        follower.update();
//
//        timer.resetTimer();
//        waitForStart();
//        follower.startTeleOpDrive();
//        follower.update();
//        while (opModeIsActive()) {
//
//            if(gamepad1.dpadUpWasPressed()){
//                startRPM = 4000;
//            } else if (gamepad2.dpadDownWasPressed()) {
//                startRPM = 2800;
//            } else if (gamepad2.x) {
//                startRPM = 0;
//            }
//
//            if(gamepad1.dpadRightWasPressed()){
//                kD += 0.0001;
//            } else if (gamepad1.dpadLeftWasPressed()) {
//                kD -=0.0001;
//            }
//
//            getX = follower.getPose().getX();
//            getY = follower.getPose().getY();
//
//            distanceToGoalRed = (Math.sqrt(Math.pow(132-(getX),2) + Math.pow(132-getY,2)));
//
//
//            if(gamepad1.right_bumper) startRPM = 19.1141*distanceToGoalRed + 1364.70076;
//            else if (gamepad1.x) startRPM = 0;
//            else if (gamepad1.left_bumper) startRPM = -1000;
//
//
//            shooterMotor.setRPM(startRPM);
//
//            xRed = Math.abs(-follower.getPose().getX());
//            xBlue = Math.abs(144-follower.getPose().getX());
//            y = Math.abs(144-follower.getPose().getY());
//            redHeading = 90-Math.toDegrees(Math.atan2(y,xBlue));
//            blueHeading = 180-Math.toDegrees(Math.atan2(y,xRed));
//
//            alignPID.setTargetPosition(blueHeading);
//
//            double BotHeading = Math.toDegrees(follower.getHeading());
//            follower.update();
//            if(gamepad1.a){
//                headingSetPoint = blueHeading;
//                AutoRun = true;
//            }
//            else if(gamepad1.b){
//                headingSetPoint = redHeading;
//                AutoRun = true;
//            }
//            else{
//                AutoRun = false;
//            }
//
//            if(gamepad1.dpadUpWasPressed()){
//                kP += 0.01;
//            } else if (gamepad1.dpadDownWasPressed()) {
//                kP -= 0.01;
//            }
//
//            double rx = -gamepad1.right_stick_x;
//
//            normalizeAngle(error);
//
//            alignPID.updatePosition(BotHeading);
//
//            if(AutoRun){
//                if(timer.getElapsedTime() > UPDATE_MS){
//                    rx = alignPID.run();
//                }
//                // Clamp the turning power to prevent overshooting and maintain accuracy [00:03:27]
//                rx = Range.clip(rx, -1, 1);
//                telemetry.addData("Error: ",error);
//            }
//
//            intake.update(gamepad1.right_trigger, gamepad1.left_trigger);
//            shooter.update(gamepad1.dpadUpWasPressed(), gamepad1.dpadDownWasPressed(),gamepad1.yWasPressed(),gamepad1.xWasPressed(),false);
//
//
//            //DriveTrain
//
//            follower.setTeleOpDrive(-gamepad1.left_stick_y, -gamepad1.left_stick_x, rx, true);
//
//
//
//
//            //Telemetry Update
//            telemetry.addData("Bot Heading: ",BotHeading);
//            telemetry.addData("AutoRun: ",AutoRun);
//            telemetry.addData("Rx: ", rx);
//            telemetry.addData("Heading Set Point: ", headingSetPoint);
//            telemetry.addData("P: ", kP);
//            telemetry.addData("D", kD);
//            telemetry.addData("X: ", follower.getPose().getX());
//            telemetry.addData("Y", follower.getPose().getY());
//            telemetry.addData("BlueX: ", xBlue);
//            telemetry.addData("BlueX: ", xRed);
//            telemetry.addData("Blue Heading: ", blueHeading);
//            telemetry.addData("Red Heading: ", redHeading);
//            telemetry.addData("Distance To Blue Goal: ", distanceToGoalBlue);
//            telemetry.addData("RPM", startRPM);
//            telemetry.update();
//        }
//    }
//    public double normalizeAngle(double angle){
//        while (angle > 180)  angle -= 360;
//        while (angle <= -180) angle += 360;
//        return angle;
//    }
//}
