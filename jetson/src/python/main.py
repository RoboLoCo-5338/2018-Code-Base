import cv2
import numpy as np

# vid = cv2.VideoCapture(0)
vid = cv2.VideoCapture("nvcamerasrc ! video/x-raw(memory:NVMM), width=(int)960, height=(int)540,format=(string)I420, framerate=(fraction)60/1 ! nvvidconv flip-method=2 ! video/x-raw, format=(string)BGRx ! videoconvert ! video/x-raw, format=(string)BGR ! appsink")
vid.set(10,.05)

def onmouse(k,x,y,s,p):
    global hsv
    if k==1:   # left mouse, print pixel at x,y
        print(hsv[y,x])

# cv2.namedWindow("hsv")
# cv2.setMouseCallback("hsv", onmouse);

while(True):
    ret, frame = vid.read()
    # frame = cv2.resize()

    #HSV SEGMENTATION
    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)

    lower_col = np.array([20, 100, 100])
    upper_col = np.array([30, 255, 255])

    mask = cv2.inRange(hsv, lower_col, upper_col)
    res = cv2.bitwise_and(frame,frame,mask=mask)

    empty, thresh = cv2.threshold(res, 150, 255, 0)

    contim = cv2.cvtColor(thresh, cv2.COLOR_HSV2BGR)
    contim = cv2.cvtColor(contim, cv2.COLOR_BGR2GRAY)

    im2, contours, hierarchy = cv2.findContours(contim, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)
    cv2.drawContours(contim, contours, -1, (255, 0, 0), 3)

    #draw dem
    for c in contours:
        rect = cv2.boundingRect(c)
        x, y, w, h = rect

        minw = 125 # FOR 720p: 165
        minh = 125 # FOR 720p: 165
        if w>minw and h>minh:
            print((x, y), (w, h))
            cv2.rectangle(frame, (x, y), (x + w, y + h), (255.0, 0, 0), 2)
            cv2.putText(frame, 'Box', (x + w + 10, y + h), 0, 0.5, (0, 255, 0))

    # cv2.imshow('original',frame)
    # cv2.imshow('hsv', hsv)
    # cv2.imshow('masked',res)
    imshow(frame)
    show()
    clear_output(wait=True)
#     if cv2.waitKey(1) & 0xFF == ord('q'):
#         break

vid.release()
cv2.destroyAllWindows()
