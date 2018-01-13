# 2018-Code-Base
Our code for the 2018 FRC Season (Power Up!), Team 5338.

## Installation
Welcome new RoboLoCo programmer! To begin the installation process, first verify that on your Windows/MacOS/Linux system you have the latest version of **JAVA 8** installed on your system. These following steps will **fail** if you do not have that exact version installed.

Next, download the code from this repository only using git clone or the download zip option, and place into the folder of your choice. 

Next, navigate to using a command line to that folder. Special note for MacOS/Linux users, here you will have to run the command "chmod +x gradlew"

On the first time that you run, you **must** be connected the actual internet. You will also need to comment out (use //) the line 29 with deployJetson.execute() in the file called build.gradle in the jetson folder. Once you have edited that file with the text editor of your choice, go back to the command line and run "./gradlew build."

This will run for a bit and proceed to pull all the dependencies that you need to run, including every library that we will be using this year. At this point, undo the commenting you did earlier, and you should be good to go. You are now able to deploy code to both the RoboRio and the Jetson TX1. Happy coding!

For more information, including how to link to IntelliJ IDEA or Eclipse, please visit the [GradleRio Github repo](https://github.com/Open-RIO/GradleRIO).

//TODO Explanation of ssh setup.
