package org.firstinspires.ftc.teamcode.OpMode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Logical.ShooterCalc;
import org.firstinspires.ftc.teamcode.Physical.Shooter.MotorController;

/**
 * This is a basic TeleOp program for a Mecanum drive robot.
 * It uses the motor names specified by the user:
 * - "lf" for leftFront
 * - "lr" for leftRear
 * - "rf" for rightFront
 * - "rr" for rightRear
 *
 * The drive controls are:
 * - Left Stick Y: Forward/Backward
 * - Left Stick X: Strafe Left/Right
 * - Right Stick X: Turn Left/Right
 */
@TeleOp(name = "TeleOpLm2", group = "Linear OpMode")
public class TeleOpLm2 extends LinearOpMode {

    // Declare OpMode members for each of the 4 motors.

    MotorController motorController;
    DcMotor leftFront = null;
    DcMotor leftRear = null;
    DcMotor rightFront = null;
    DcMotor rightRear = null;
    DcMotor intake;
    DcMotorImplEx shooter;
    Servo transfer;
    Servo indexer;
    Servo angle;
    int i = 0;
    int j = 0;
    boolean dpad_right = false;
    public ElapsedTime timer = new ElapsedTime();

    double distanceToGoal = 0;
    double startRPM = 0;



    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        // Initialize the hardware variables.
        // These names MUST match the names in the robot configuration
        // on the Robot Controller app.
        leftFront  = hardwareMap.get(DcMotor.class, "lf");
        leftRear   = hardwareMap.get(DcMotor.class, "lr");
        rightFront = hardwareMap.get(DcMotor.class, "rf");
        rightRear  = hardwareMap.get(DcMotor.class, "rr");


        // ********************** SHOOTER/TRANSFER *************************
        shooter = hardwareMap.get(DcMotorImplEx.class,"shooter");
        transfer = hardwareMap.get(Servo.class, "transfer");
        angle = hardwareMap.get(Servo.class, "angle");
        motorController = new MotorController(shooter);


        // *********************** INDEXER/INTAKE **************************
        intake = hardwareMap.get(DcMotor.class, "intake");
        indexer = hardwareMap.get(Servo.class, "indexer");


        // *** CRITICAL STEP: REVERSE MOTORS AS NEEDED ***

        // *****************************************************************
        // *** CRITICAL STEP: REVERSE MOTORS AS NEEDED ***
        // *****************************************************************
        // Most robots need the motors on one side to be reversed.
        // If your robot drives backward when you push forward,
        // reverse the direction of all four motors.
        // If your robot spins when you push forward, reverse the
        // direction of the two motors on one side.
        //
        // This configuration assumes the motors on the RIGHT side
        // are mounted in reverse.
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftRear.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightRear.setDirection(DcMotor.Direction.FORWARD);

        // Set all motors to brake when power is 0.
        // This helps the robot stop more quickly.
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Wait for the game to start (driver presses PLAY)
        telemetry.addData("Status", "Initialized");
        telemetry.addData(">", "Press Start to drive");
        telemetry.update();

        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // ********************** INTAKE *************************
            if(gamepad1.right_trigger > 0.01){
                intake.setPower(-gamepad1.right_trigger);
            }
            else if (gamepad1.left_trigger > 0.01) {
                intake.setPower(gamepad1.left_trigger);
            }
            else{
                intake.setPower(0);
            }

            // ********************** SHOOTER/ANGLE ************************
//            if(gamepad1.right_bumper){
//                shooter.setPower(-1);
//            }
//            else if(gamepad1.left_bumper){
//                shooter.setPower(1);
//            }
//            else{
//                shooter.setPower(0);
//            }
            if(gamepad2.dpad_down){
                angle.setPosition(gamepad2.right_stick_y);
            }
            distanceToGoal = ShooterCalc.distance(60,6, false);
            double v0 = ShooterCalc.v0(distanceToGoal);
            double angleDeg = ShooterCalc.angle(distanceToGoal);
            angle.setPosition(ShooterCalc.angleToServo(angleDeg));
            motorController.setRPM(-ShooterCalc.velocityToRPM(v0));
//            motorController.setRPM(startRPM);

            telemetry.addData("v0", v0);
            telemetry.addData("RPM", ShooterCalc.velocityToRPM(v0));
            telemetry.addData("angleDeg",angleDeg);
            telemetry.addData("Angle Servo Position", ShooterCalc.angleToServo(angleDeg));
            telemetry.addData("Distance", distanceToGoal);

            if(gamepad2.right_bumper){
                startRPM+= 10;
            }
            if(gamepad2.left_bumper){
                startRPM-= 10;
            }
            if(gamepad2.right_trigger>0.01){
                startRPM += 100;
            }
            if(gamepad2.left_trigger>0.01){
                startRPM -= 100;
            }
            if(gamepad2.a){
                startRPM = 0;
            }


            // ********************** INDEX/TRANSFER ***********************
            double[] shooterList = {0.1,0.54,1};
            double[] intakeList = {0,0.33,0.7939};
//            double[] servoList = {0, 0.1, 0.33, 0.54, 0.7939, 1};
//            if (gamepad1.b) {
//                timer.reset();
//                i++;
//                if(i>shooterList.length-1){
//                    i = 0;
//                }
//                if(i<0){
//                    i = shooterList.length-1;
//                }
//                indexer.setPosition(shooterList[i]);
//                while(timer.milliseconds() <100){
//                    telemetry.update();
//                }
//            }
//            if (gamepad1.x) {
//                j++;
//                if(j<0){
//                    j = intakeList.length-1;
//                }
//                if(j>intakeList.length -1 ){
//                    j = 0;
//                }
//                indexer.setPosition(intakeList[j]);
//                while(timer.milliseconds() <100){
//                    telemetry.update();
//                }
//            }
            if(gamepad1.dpad_right){
                if(gamepad1.b){
                    indexer.setPosition(intakeList[0]);
                }
                if(gamepad1.x){
                    indexer.setPosition(intakeList[1]);
                }
                if(gamepad1.y){
                    indexer.setPosition(intakeList[2]);
                }
            }
            else{
                if(gamepad1.b){
                    indexer.setPosition(shooterList[0]);
                }
                if(gamepad1.x){
                    indexer.setPosition(shooterList[1]);
                }
                if(gamepad1.y){
                    indexer.setPosition(shooterList[2]);
                }
            }
            if(gamepad1.a){
                transfer.setPosition(0);
                sleep(500);
                transfer.setPosition(0.3444);
            }
            double flywheelRPM = (motorController.getCurrentRPM()*22)/31;

            //************************ TELEMETRY **************************
            telemetry.addData("Status", "Running");
            telemetry.addData(">", "---");
//            telemetry.addData("Indexer", indexer.getPosition());
//            telemetry.addData("Transfer", transfer.getPosition());
            telemetry.addData("angle", angle.getPosition());
            telemetry.addData("Target RPM", startRPM);
            telemetry.addData("Flywheel RPM", flywheelRPM);
            telemetry.addData("Motor RPM", (motorController.getCurrentRPM()));
//            telemetry.addData("distanceToGoal", distanceToGoal);
            telemetry.update();
        }
    }
    public void Drivetrain(){

        // --- Gamepad Input ---
        // Y-axis (Forward/Backward)
        // Note: The Y axis on the gamepad is inverted.
        // Pushing forward on the stick gives a negative value.
        // We multiply by -1 to correct this.
        double y = -gamepad1.left_stick_y;

        // X-axis (Strafing)
        double x = gamepad1.left_stick_x;

        // RX-axis (Turning)
        double rx = gamepad1.right_stick_x;

        // --- Mecanum Drive2 Logic ---
        // This logic combines the Y, X, and RX inputs to calculate
        // the power for each of the four motors.

        // Denominator is the largest motor power (absolute value)
        // or 1, whichever is greater.
        // This is used to scale all motor powers down proportionally
        // if any motor power is greater than 1.0.
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1.0);

        double leftFrontPower = (y - x + rx) / denominator;
        double leftRearPower = (y + x + rx) / denominator;
        double rightFrontPower = (y - x - rx) / denominator;
        double rightRearPower = (y + x - rx) / denominator;

        // --- Set Motor Powers ---
        // Send the calculated power to the motors.
        leftFront.setPower(leftFrontPower);
        leftRear.setPower(leftRearPower);
        rightFront.setPower(rightFrontPower);
        rightRear.setPower(rightRearPower);

        // --- Telemetry ---
        // Send status and motor power data to the Driver Station.
        telemetry.addData("Status", "Running");
        telemetry.addData(">", "---");
        telemetry.addData("Inputs", "Y: %.2f, X: %.2f, RX: %.2f", y, x, rx);
        telemetry.addData(">", "---");
        telemetry.addData("Left Front (lf)", "%.2f", leftFrontPower);
        telemetry.addData("Left Rear (lr)", "%.2f", leftRearPower);
        telemetry.addData("Right Front (rf)", "%.2f", rightFrontPower);
        telemetry.addData("Right Rear (rr)", "%.2f", rightRearPower);
        telemetry.update();
    }
}