import cv2
import numpy as np
from multiprocessing import Pool

cv2.setUseOptimized(True)
vid = cv2.VideoCapture(0)

lower_col, upper_col = np.array([20, 100, 100]), np.array([30, 255, 255])
minw, minh = 125, 125

frames = 0

def process_frame(frame,frames):
    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)

    mask = cv2.inRange(hsv, lower_col, upper_col)
    res = cv2.bitwise_and(frame, frame, mask=mask)

    empty, thresh = cv2.threshold(res, 150, 255, 0)

    contim = np.array(thresh[:,:,2])

    im2, contours, hierarchy = cv2.findContours(contim, mode=cv2.RETR_LIST, method=cv2.CHAIN_APPROX_SIMPLE)

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
        print("CRD: {} {}".format(repr(((x + w/2, y + h/2),(w, h))),frames))
    else:
        print("CRD: {} {}".format(None,frames))


while(True):
    ret, frame = vid.read()
    frames += 1
    with Pool(processes=4) as pool:
        pool.apply_async(process_frame,(frame,frames))

vid.release()
