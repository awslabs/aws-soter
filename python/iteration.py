#!/usr/bin/env python3

import sys
import grpc
import time
import threading

from concurrent import futures

from soter import Soter, SoterThread

global soter
soter = Soter()

if __name__ == "__main__":
    soter.log("Program started.")
    soter.stop()
    soter.log("Stopped server.")