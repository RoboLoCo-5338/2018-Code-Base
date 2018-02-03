##Requirements
User named roboloco with access as a member of sudo and video groups.
SD card as a home directory for TX1, ext4 formatted, or using onboard storage on TX2.
roboloco.service moved to /lib/systemd/system
Activate systemd changes using systemctl daemon-reload
Enable service on boot using systemctl enable roboloco
wifi-Gp.sh and wifi-lcps.sh moved to /root
tegrastats moved to /usr/bin
code directory with active subfolder should be in roboloco home directory
On TX2, fix two cores being disabled by editing /etc/nvpmodel.conf and changing the default state to 2

##Dependencies
Run the commands from this (site)[https://jkjung-avt.github.io/opencv3-on-tx2/]
python3.5, python3-pip, python3-dev, and python3-tk must be installed using apt
tmux, screen, atop, htop, and vim should be installed using apt
pynetworktables, matplotlib, and numpy should be installed using sudo and pip3
sudo apt-get purge libopencv4tegra-python libopencv4tegra-dev \
                     libopencv4tegra
sudo apt-get purge libopencv4tegra-repo
sudo apt-get purge python-numpy
sudo apt autoremove
sudo apt-get update
sudo apt-get upgrade
sudo apt-get install build-essential make cmake cmake-curses-gui \
                       g++ libavformat-dev libavutil-dev \
                       libswscale-dev libv4l-dev libeigen3-dev \
                       libglew-dev libgtk2.0-dev
sudo apt-get install libdc1394-22-dev libxine2-dev \
                       libgstreamer1.0-dev \
                       libgstreamer-plugins-base1.0-dev
sudo apt-get install libjpeg8-dev libjpeg-turbo8-dev libtiff5-dev \
                       libjasper-dev libpng12-dev libavcodec-dev
sudo apt-get install libxvidcore-dev libx264-dev libgtk-3-dev \
                       libatlas-base-dev gfortran
sudo apt-get install qt5-default
sudo vim /usr/local/lib/python3.5/dist-packages/matplotlib/mpl-data/matplotlibrc and change line 41 to 'backend      : TkAgg'
sudo vim /usr/local/cuda-8.0/include/cuda_gl_interop.h and comment out line 62-66 as well as 68
cd /usr/lib/aarch64-linux-gnu/
sudo ln -sf tegra/libGL.so libGL.so

                     
