//package org.firstinspires.ftc.teamcode.OpMode;
//
//import com.pedropathing.follower.Follower;
//import com.pedropathing.geometry.Pose;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.teamcode.Coordinator.Drivetrain;
//import org.firstinspires.ftc.teamcode.Coordinator.Intake;
//import org.firstinspires.ftc.teamcode.Coordinator.Shooter;
//import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
//
//@TeleOp(name = "Duck TeleOp Blue New", group = "TeleOp")
//public class DuckTeleOpBlueNew extends OpMode {
//
//    Follower follower;
//    Pose startingPose = new Pose(55 ,30, Math.toRadians(90));
//    Intake intake;
//    Shooter shooter_turret;
//    Drivetrain drivetrain;
//
//
//    @Override
//    public void init() {
//        follower = Constants.createFollower(hardwareMap);
//        follower.setStartingPose(startingPose);
//        follower.update();
//
//        intake = new Intake(telemetry,hardwareMap,gamepad1);
//        shooter_turret = new Shooter(telemetry,hardwareMap,gamepad1,follower);
//        drivetrain = new Drivetrain(telemetry,hardwareMap,gamepad1);
//
//        shooter_turret.resetTurretMotor();
//    }
//
//    @Override
//    public void loop() {
//        follower.update();
//        intake.update(gamepad1.right_trigger, gamepad1.left_trigger);
//        shooter_turret.updateShooter(gamepad1.right_bumper, gamepad1.left_bumper,gamepad1.yWasPressed(),gamepad1.left_stick_button,gamepad1.right_stick_button, gamepad1.dpadUpWasPressed(), gamepad1.dpadDownWasPressed(), gamepad1.dpadRightWasPressed(), gamepad1.dpadLeftWasPressed(), false);
////        shooter_turret.updateTurret(false, gamepad1.xWasPressed());
//        drivetrain.update(-gamepad1.left_stick_y, -gamepad1.left_stick_x, gamepad1.right_stick_x);
//
//        telemetry.update();
//    }
//}
