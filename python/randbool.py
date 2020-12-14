#!/usr/bin/env python3

import sys
import grpc
import time
import threading
import random

from concurrent import futures

from soter import Soter, SoterThread, SoterRandom

global soter
soter = Soter()

random = SoterRandom()

if __name__ == "__main__":
    soter.log("Program started.")

    x = random.randbool("0")
    print("x =", x)

    y = random.randbool("0")
    print("y =", y)

    if x == y:
        soter.stop()
        sys.exit("This is a crash.")

    soter.stop()
    soter.log("Stopped server.")