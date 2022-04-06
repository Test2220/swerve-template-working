package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;

public class DigitalIO {

    private DigitalInput digitalInput;
    private boolean inverted;

    public DigitalIO(int port, boolean inverted) {

        digitalInput = new DigitalInput(port);
        this.inverted = inverted;
        
    }

    public boolean get() {

        if (inverted) {
            return !digitalInput.get();
        } else {
            return digitalInput.get();
        }

    }

}
