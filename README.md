# VulnerableMobileApplications
This repository contains several mobile applications that are vulnerable to various exploits and misconfigurations. This includes Redshift's intentionally vulnerable android application (Vapour), a python server for the Vapour applcation and an SSL bypass android application.

These applications were created as part of a mobile pentesting training series to help grow and improve the mobile cyber security spcae. Each application's descriptions and details could be found in their respective folders as well as their use/funtionality and/or the vulnerabilities associated with them.

### RS_VapourTraining
An intentionally vulnerable Android application that's vulnerable to common mobile vulnerabilities and weaknesses. This mobile application also requires the below mentioned python server to be running.

### RS_VapourTraining_app_server
The intentionally vulnerabile Android application, Vapour's python server. This python server also generated a IP address that should be used in the Vapour application.

### RS_Certpinning_bypass
A mobile application that could be used to learn how to bypass basic certificate pinning.

## How to use

### Certificate Pinning Bypass Application
1. Download latest release version of the application.
2. Install APK on an android device (Preferably an emulated Pixel 4 Device)
3. Enjoy

### The Vapour Vulnerable Traing Application
1. Download latest release version of the application and it API server.
2. Install APK on an android device (Preferably an emulated Pixel 4 Device)
3. Run API server by opening a terminal (or cmd/Powershell whatever tickels your fancy) and entering the command "python3 main.py"
4.  Enjoy

**Note the Vapour api server requires python to run**

