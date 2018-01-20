import cv2
import numpy as np
from multiprocessing import Pool
from networktables import NetworkTablesInstance
import logging
import os
import signal

logging.basicConfig(level=logging.INFO)
root_log = logging.getLogger()
robo_log = root_log.getChild('roboloco')
nt_log = root_log.getChild('nt')
nt_log.setLevel(logging.DEBUG)
log = robo_log.getChild('main')

# Video capture device
# Same as /dev/videoX
VCAP = 0

# Roborio's IP address or hostname
NT_SERVER = ("10.53.38.2", 5800)

# NetworkTables table
NT_TABLE = 'vision'

global keep_running
keep_running = True

log.debug("Setting up CV2")
cv2.setUseOptimized(True)

log.debug("Opening vcap %d", VCAP)
vid = cv2.VideoCapture(VCAP)

log.debug("Init'ing data storage")
lower_col, upper_col = np.array([20, 100, 100]), np.array([80, 255, 255])
minw, minh = 0, 0


def process_frame(frame, frame_id):
    log = logging.getLogger('roboloco.main.frame_%d.%d' % (frame_id, os.getpid()))
    log.debug("Working on frame %d", frame_id)

    NetworkTables = NetworkTablesInstance.create()
    log.debug("Init'ing network tables connection to %r", NT_SERVER)
    NetworkTables.initialize(server=NT_SERVER)
    # NetworkTables.setServerTeam(5338, 5800)

    log.debug("Getting table %r", NT_TABLE)
    table = NetworkTables.getTable(NT_TABLE)
    NetworkTables.enableVerboseLogging()

    log.info("Network mode %r", NetworkTables.getNetworkMode())

    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)

    mask = cv2.inRange(hsv, lower_col, upper_col)
    res = cv2.bitwise_and(frame, frame, mask=mask)

    empty, thresh = cv2.threshold(res, 150, 255, 0)

    contim = np.array(thresh[:, :, 2])

    im2, contours, hierarchy = cv2.findContours(contim, mode=cv2.RETR_LIST, method=cv2.CHAIN_APPROX_SIMPLE)

    max_rect = None
    max_size = 0
    for index, c in enumerate(contours):
        rect = cv2.boundingRect(c)
        x, y, w, h = rect
        if w > minw and h > minh:
            if w * h > max_size:
                max_rect = rect
    ret = None
    if max_rect is not None:
        x, y, w, h = max_rect
        result = table.putNumber('x', x)
        if frame_id % 30 == 0:
            log.info("Return result %r", result)
            log.info("Table Value %r", table.getNumber("x", -42.0))
            log.info("Table Keys %r", table.getKeys())
            #log.info("Connection info %r", NetworkTables.getConnections())
        NetworkTables.flush()
        #table.putNumber('y', y)
        #table.putNumber('w', w)
        #table.putNumber('h', h)
        if frame_id % 30 == 0: log.debug(" ".join([str(i) for i in (x, y, w, h)]))
        ret = x
    else:
        if frame_id % 30 == 0: log.info("Not found")
        ret = None
    NetworkTables.flush()
    NetworkTables.stopClient()
    return ret


def sigint_handler(signal, frame):
    """ Run whenever ctrl-c is pressed aka SIGINT signal """
    log.warning("Exit requested - %r received", signal)
    global keep_running
    keep_running = False


# Capture ctrl-c (aka SIGINT)
log.info("Registered sigint handler")
signal.signal(signal.SIGINT, sigint_handler)

try:
    log.info("Starting main loop")
    frame_id = 0
    while (keep_running):
        log_frame = log.getChild('frame_%d' % frame_id)
        log_frame.debug("Fetching frame %d", frame_id)
        ret, frame = vid.read()

        log_frame.debug("Launching frame")
        with Pool(processes=4) as pool:
            result = pool.apply_async(process_frame, (frame, frame_id))
            if frame_id % 30 == 0: log.info("Result: %r", result.get())
            # print(result.get())  # Avoid enabling this - will force async jobs to only run one at a time!
        log_frame.debug("Frame processing launched")
        frame_id += 1
    log.info("Exited loop")
except Exception as e:
    # Includes stack trace
    log.exception("Error during main loop %r", e)

log.info("Releasing video device")
vid.release()
log.info("Video device released")
