//package org.firstinspires.ftc.teamcode.Logical;
//
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.opencv.core.Mat;
//
//public class ShooterCalc {
//
//    static double yf = 1.2;
//    public static double distance(double x, double y, boolean isRed){
//        if(!isRed){
//            //get Heading
//            return (Math.sqrt(Math.pow(12-x,2) + Math.pow(132-y,2)))/(39.37);
//        }
//        return (Math.sqrt(Math.pow(132-x,2) + Math.pow(132-y,2)))/(39.37);
//    }
//
//    public static double v0(double xf){
//        // Break down the numerator for clarity
//        double numerator = 9.8 * (Math.pow(xf, 2) + 4 * Math.pow(yf, 2));
//
//        // Calculate the denominator
//        double denominator = 2 * yf;
//
//        // Return the square root of the result
//        return Math.sqrt(numerator / denominator)*1.15;
//    }
////    public static double angle(double xf) {
////        double g = 9.8;
////        double v0 = v0(xf);
////
////        // Common sub-expressions
////        double v0Squared = v0 * v0;
////        double gx = -g * xf;
////
////        // Inside the square root
////        double sqrtTerm = 1
////                - (2 * gx) / v0Squared
////                * (gx / (2 * v0Squared) - (yf / xf));
////
////        // Square root
////        double sqrtValue = Math.sqrt(sqrtTerm);
////
////        // Numerator and denominator
////        double numerator = -1 + sqrtValue;
////        double denominator = gx / v0Squared;
////
////        // Final result
////        return Math.toDegrees(Math.atan(numerator / denominator));
////    }
//    public static double velocityToRPM(double x){
//        double a = 86.09621;
//        double b = -1388.66722;
//        double c = 7825.72452;
//        double d = -12627.7772;
//
//        // Applying the cubic formula: ax^3 + bx^2 + cx + d
//        return ((a * x * x * x) + (b * x * x) + (c * x) + d)*1.41;
//        return 525.34014 * x - 131.25767;
//    }
//
//    public static double angleToServo(double x){
//        double a = -0.0403211;
//        double b = 1.93541;
//        return (a * x) + b;
//    }
//    //60,16
//}
