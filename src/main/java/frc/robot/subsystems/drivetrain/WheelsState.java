package frc.robot.subsystems.drivetrain;

import static frc.robot.Constants.*;

public class WheelsState {
    private double wsFR, wsFL, wsBR, wsBL;
    private double waFR, waFL, waBR, waBL;

    /**
     * Creates a WheelsState object containing the positions and speeds of each wheel from a DriveDirection object.
     * 
     * @param in DriveDirection object containing the movement and rotation that you want
     */
    public WheelsState(DriveDirection in) {
        double fwd = in.getFwd();
        double str = in.getStr();
        double rot = in.getRot();

        double a = str - rot * (DRIVETRAIN_LENGTH_METERS / DRIVETRAIN_DIAMETER);
        double b = str + rot * (DRIVETRAIN_LENGTH_METERS / DRIVETRAIN_DIAMETER);
        double c = fwd - rot * (DRIVETRAIN_WIDTH_METERS / DRIVETRAIN_DIAMETER);
        double d = fwd + rot * (DRIVETRAIN_WIDTH_METERS / DRIVETRAIN_DIAMETER);

        // Get wheel speeds
        wsFR = Math.sqrt((b * b) + (c * c));
        wsFL = Math.sqrt((b * b) + (d * d));
        wsBR = Math.sqrt((a * a) + (c * c));
        wsBL = Math.sqrt((a * a) + (d * d));

        // Get wheel angles
        waFR = -Math.atan2(b, c);
        waFL = -Math.atan2(b, d);
        waBR = -Math.atan2(a, c);
        waBL = -Math.atan2(a, d);

        // Normalize values to 1 if any are greater than 1
        double wsMAX = Math.max(Math.max(wsFR, wsFL),Math.max(wsBR, wsBL));
        if (wsMAX > 1) {
            wsFR /= wsMAX;
            wsFL /= wsMAX;
            wsBR /= wsMAX;
            wsBL /= wsMAX;
        }
    }

    public WheelsState(double waFR, double waFL, double waBR, double waBL, double wsFR, double wsFL, double wsBR, double wsBL) {
        this.waFL = waFL;
        this.waFR = waFR;
        this.waBL = waBL;
        this.waBR = waBR;

        this.wsFR = wsFR;
        this.wsFL = wsFL;
        this.wsBR = wsBR;
        this.wsBL = wsBL;
    }

    /**
     * Gets the angles of a WheelsState object.
     * 
     * @return Double List, in order of FR, FL, BR, BL
     */
    public double[] getAngles() {
        double[] out = {waFR, waFL, waBR, waBL};
        return out;
    }

    /**
     * Gets the speeds of a WheelsState object.
     * 
     * @return Double List, in order of FR, FL, BR, BL
     */
    public double[] getSpeeds() {
        double[] out= {wsFR, wsFL, wsBR, wsBL};
        return out;
    }

    public double[] getFR() {
        double[] out = {wsFR, waFR};
        return out;
    }

    public double[] getFL() {
        double[] out = {wsFL, waFL};
        return out;
    }

    public double[] getBR() {
        double[] out = {wsBR, waBR};
        return out;
    }

    public double[] getBL() {
        double[] out = {wsBL, waBL};
        return out;
    }

    public void optimizePos(WheelsState in) {
        double[] angles = in.getAngles();
        double[] anglesNew = this.getAngles();
        double[] speedsNew = this.getSpeeds();

        for (int i = 0; i < 4; i++) {
            if (speedsNew[i] < 0.02) {
                anglesNew[i] = angles[i];
                speedsNew[i] = 0;
                continue;
            }

            angles[i] += Math.PI;
            anglesNew[i] += Math.PI;
            
            double change = Math.abs(angles[i] - anglesNew[i]);
            
            if (change > (Math.PI / 2) && change < (Math.PI * 1.5)) {
                anglesNew[i] = (anglesNew[i] + Math.PI);
                speedsNew[i] = -speedsNew[i];
            } else if (change > (Math.PI * 1.5)) {
                anglesNew[i] += (Math.PI * 2 * ((int)(angles[i] / Math.PI) - 1));
            }

            anglesNew[i] -= Math.PI;
        }

        waFR = anglesNew[0];
        wsFR = speedsNew[0];

        waFL = anglesNew[1];
        wsFL = speedsNew[1];
        
        waBR = anglesNew[2];
        wsBR = speedsNew[2];

        waBL = anglesNew[3];
        wsBL = speedsNew[3];
    }

    // public static WheelsState convertFromWheelsState(WheelsState[] in) {

    // }
}

