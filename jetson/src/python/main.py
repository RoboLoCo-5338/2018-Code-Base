import cv2
import numpy as np
from multiprocessing import Pool
import logging
import os
import signal
from networktables import NetworkTablesInstance
from http.server import BaseHTTPRequestHandler, HTTPServer
from socketserver import ThreadingMixIn
import time
from PIL import Image
from io import StringIO, BytesIO
import threading

logging.basicConfig(level=logging.INFO)
root_log = logging.getLogger()
robo_log = root_log.getChild('roboloco')
nt_log = root_log.getChild('nt')
nt_log.setLevel(logging.DEBUG)
log = robo_log.getChild('main')

# Video capture device
# Same as /dev/videoX
VCAP = 0

# Store latest image used by web server
img = None

# Roborio's IP address or hostname
NT_PORT = 5800
TEAM = 5338
NT_HOST = '10.53.38.2'
# NetworkTables table
NT_TABLE = 'vision'

global keep_running
keep_running = True

log.debug("Setting up CV2")
cv2.setUseOptimized(True)

log.debug("Opening vcap %d", VCAP)
vid = cv2.VideoCapture(VCAP)
vid.set(3, 640)
vid.set(4, 480)

log.debug("Init'ing data storage")
lower_col, upper_col = np.array([20, 100, 100]), np.array([80, 255, 255])
minw, minh = 100, 100

nt_inst = NetworkTablesInstance.create()
nt_inst.enableVerboseLogging()
log.debug("Init'ing network tables connection to %r via %r", TEAM, NT_PORT)

nt_inst.initialize(server=(NT_HOST, NT_PORT))

log.debug("Getting table %r", NT_TABLE)
table = nt_inst.getTable(NT_TABLE)

log.info("Network mode %r", nt_inst.getNetworkMode())


def process_frame(frame, frame_id):
    log = logging.getLogger('roboloco.main.frame_%d.%d' % (frame_id, os.getpid()))
    log.debug("Working on frame %d", frame_id)

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
    table.putBoolean("CubeDetected", False)
    if max_rect is not None:
        x, y, w, h = max_rect
        table.putBoolean("CubeDetected", True)
        table.putNumber('XCoordinate', x)
        table.putNumber('YCoordinate', y)
        table.putNumber('Width', w)
        table.putNumber('Height', h)
        if frame_id % 30 == 0:
            log.info("Table Keys %r", table.getKeys())
            # log.info("Connection info %r", NetworkTables.getConnections())
        if frame_id % 30 == 0:
            log.debug(" ".join([str(i) for i in (x, y, w, h)]))
    else:
        if frame_id % 30 == 0:
            log.info("Not found")
    global img
    imgRGB = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
    thumbnail = cv2.resize(imgRGB, (640 / 2, 320 / 2), interpolation=cv2.INTER_CUBIC)
    img = Image.fromarray(frame)


def sigint_handler(signal, frame):
    """ Run whenever ctrl-c is pressed aka SIGINT signal """
    log.warning("Exit requested - %r received", signal)
    global keep_running
    keep_running = False


class CamHandler(BaseHTTPRequestHandler):
    def do_GET(self):
        if self.path.endswith('.mjpg'):
            self.send_response(200)
            self.send_header('Content-type', 'multipart/x-mixed-replace; boundary=--jpgboundary')
            self.end_headers()
            while True:
                try:
                    tmpFile = BytesIO()
                    img.save(tmpFile, 'JPEG')
                    self.wfile.write(b"--jpgboundary")
                    self.send_header('Content-type', 'image/jpeg')
                    # self.send_header('Content-length',str(tmpFile.len))
                    self.end_headers()
                    img.save(self.wfile, 'JPEG')
                    time.sleep(0.05)
                except KeyboardInterrupt:
                    break
            return
        if self.path.endswith('.html'):
            self.send_response(200)
            self.send_header('Content-type', 'text/html')
            self.end_headers()
            self.wfile.write(b'<html><head></head><body>')
            self.wfile.write(b'<img src="http://10.53.38.75:5805/cam.mjpg"/>')
            self.wfile.write(b'</body></html>')
            return


class ThreadedHTTPServer(ThreadingMixIn, HTTPServer):
    """Handle requests in a separate thread."""


class WebServerThread(threading.Thread):
    def run(self):
        server = ThreadedHTTPServer(('0.0.0.0', 5805), CamHandler)
        server.serve_forever()


# Capture ctrl-c (aka SIGINT)
log.info("Registered sigint handler")
signal.signal(signal.SIGINT, sigint_handler)

wt = WebServerThread(daemon=True)
wt.start()

try:
    log.info("Starting main loop")
    frame_id = 0
    while (keep_running):
        log_frame = log.getChild('frame_%d' % frame_id)
        log_frame.debug("Fetching frame %d", frame_id)
        ret, frame = vid.read()

        log_frame.debug("Launching frame")
        process_frame(frame, frame_id)
        log_frame.debug("Frame processing launched")
        frame_id += 1
    log.info("Exited loop")
except Exception as e:
    # Includes stack trace
    log.exception("Error during main loop %r", e)

log.info("Releasing video device")
vid.release()
log.info("Video device released")

nt_inst.flush()
nt_inst.stopClient()
