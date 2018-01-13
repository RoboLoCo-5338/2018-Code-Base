import cv2
import numpy as np
import thread

vid = cv2.VideoCapture("nvcamerasrc ! video/x-raw(memory:NVMM), width=(int)1280, height=(int)720,format=(string)I420, framerate=(fraction)60/1 ! nvvidconv flip-method=2 ! video/x-raw, format=(string)BGRx ! videoconvert ! video/x-raw, format=(string)BGR ! appsink")

lower_col, upper_col = np.array([20, 100, 100]), np.array([30, 255, 255])
minw, minh = 125, 125

frames = 0

def process_frame(frame):
    #HSV SEGMENTATION
    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)

    mask = cv2.inRange(hsv, lower_col, upper_col)
    res = cv2.bitwise_and(frame,frame,mask=mask)

    empty, thresh = cv2.threshold(res, 150, 255, 0)

    contim = cv2.cvtColor(thresh, cv2.COLOR_HSV2BGR)
    contim = cv2.cvtColor(contim, cv2.COLOR_BGR2GRAY)

    im2, contours, hierarchy = cv2.findContours(contim, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)
    
    max_rect = None
    max_size = 0
    for index, c in enumerate(contours):
        rect = cv2.boundingRect(c)
        x, y, w, h = rect
        if w>minw and h>minh:
            if w*h > max_size:
                max_rect = rect
    if max_rect is not None:
       x, y, w, h = max_rect
       print(((x + w/2, y + h/2),(w, h)))
    else:
       print(None)


while(True):
    ret, frame = vid.read()
    thread.start_new_thread(process_frame,(frame,))

vid.release()
