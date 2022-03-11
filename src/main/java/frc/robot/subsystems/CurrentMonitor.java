package frc.robot.subsystems;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.util.WPILibVersion;
import frc.robot.Constants.*;

public class CurrentMonitor {
    static PowerDistribution PDH = new PowerDistribution();

    public static ArrayList getOutputCurrent(){
        ArrayList<Double> currentPowerDraw = new ArrayList<Double>();
       for(int i = 0; i > 22; i++){
        currentPowerDraw.add(PDH.getCurrent(i));
        } 
        return currentPowerDraw;
    }
    
}