package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.cscore.HttpCamera.HttpCameraKind;
import edu.wpi.first.cscore.VideoMode.PixelFormat;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;


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
        HttpCamera limeLightShooter = new HttpCamera("Limelight-shooter", "http://10.22.20.45:5800/stream.mjpg");
        // limeLightShooter.setFPS(30);
        // limeLightShooter.setResolution(320, 240);
        // limeLightShooter.setPixelFormat(PixelFormat.kMJPEG);
        // //CameraServer.addCamera(limeLightShooter);
        // CameraServer.startAutomaticCapture(limeLightShooter);
        CameraServer.getVideo(limeLightShooter);


      //  Shuffleboard.getTab("Driver Tab").add(httpCamera).withSize(7, 4).withPosition(0, 0);
          Shuffleboard.getTab("Driver Tab").add(limeLightShooter);
          //.withSize(4, 4).withPosition(0, 0);

        //   HttpCamera httpCameraIntake = new HttpCamera("LimeLightCameraIntake", "http://10.22.20.211/stream.mjpg", HttpCameraKind.kMJPGStreamer);
        //   httpCameraIntake.setFPS(30);
        //   httpCameraIntake.setResolution(320, 240);
        //   httpCameraIntake.setPixelFormat(PixelFormat.kMJPEG);
        //   CameraServer.addCamera(httpCameraIntake);
  
        // //  Shuffleboard.getTab("Driver Tab").add(httpCamera).withSize(7, 4).withPosition(0, 0);
        //     Shuffleboard.getTab("Driver Tab").add(httpCameraIntake).withSize(4, 4).withPosition(4, 0);
        
    }
      
    }

