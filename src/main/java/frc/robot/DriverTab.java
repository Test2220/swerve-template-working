package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.cscore.MjpegServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoMode.PixelFormat;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class DriverTab {

    public DriverTab() {
      //  MjpegServer server = CameraServer.addServer("USB server 0");
        // UsbCamera camera = new UsbCamera("USB Camera 0", 0);
        // CameraServer.addCamera(camera);
        // server.setSource(camera);
        // server.getProperty("compression").set(10);
        // server.getProperty("default_compression").set(10);
        // server.getProperty("width").set(320);
        // server.getProperty("height").set(240);
        // camera.setResolution(320, 240);
        // server.getProperty("fps").set(22);
        // camera.setFPS(22);
        HttpCamera httpCamera = new HttpCamera("LimeLightCamera", "http://10.22.20.45:5800");
        httpCamera.setFPS(30);
        httpCamera.setResolution(320, 240);
        httpCamera.setPixelFormat(PixelFormat.kMJPEG);
        CameraServer.addCamera(httpCamera);

      //  Shuffleboard.getTab("Driver Tab").add(httpCamera).withSize(7, 4).withPosition(0, 0);
          Shuffleboard.getTab("Driver Tab").add(httpCamera).withSize(7, 4).withPosition(0, 0);
        
    }
        
    }
