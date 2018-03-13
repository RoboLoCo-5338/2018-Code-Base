# 2018-Code-Base
Our complete code for the 2018 FRC Season (Power Up!), Team 5338. This code, including the three current autonomouses, has been validated as fully operational after a Week 1 and Week 2 District Event.

## Installation
Welcome new RoboLoCo programmer! To begin the installation process, first verify that on your Windows/macOS/Linux system you have the latest version of **JAVA 8** installed on your system. These following steps will **fail** if you do not have that exact version installed.

Next, download the code from this repository using the git clone or the download zip option, and place into the folder of your choice. 

Next, navigate to the folder using the command line. Special note for macOS/Linux users, here you will have to run the command "chmod +x gradlew" to authorize the script to run.

On the very first time that you run, you **must** be connected the actual internet. You will also need to comment out (use //) the line 36 with deployJetson.execute() in the file called build.gradle in the jetson folder. Once you have edited that file with the text editor of your choice, go back to the command line and run "./gradlew build" on your system.

This will run for a bit and proceed to pull all the dependencies that you need to run, including every library that we will be using this year. At this point, undo the commenting you did earlier, and you should be good to go. You are now able to deploy code to both the RoboRio and the Jetson TX1. Happy coding!

The above two steps will also be necessary any time and update to any dependencies or plugins will be done.

For more information, including how to link to IntelliJ IDEA or Eclipse, please visit the [GradleRio Github repo](https://github.com/Open-RIO/GradleRIO). Feel free to ask any questions about setup during the meeting.
