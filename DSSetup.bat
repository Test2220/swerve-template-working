taskkill /IM "DriverStation.exe"

::Force close shuffleboard so there is no save dialog, which prevents it from restarting
taskkill /IM "javaw.exe" /F

:: 2 seconds is the minimum, 1.9 doesn't allow time to close and reopen
timeout /t 2

Start "" "C:\Program Files (x86)\FRC Driver Station\DriverStation.exe"

cd "C:\Users\User\Documents\GitHub\swerve-template-working\TwighlightDash"
npm run start