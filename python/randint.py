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

    lower = 1
    upper = 10

    x = random.randint("0", lower, upper)
    print("x =", x)

    y = random.randint("0", lower, upper)
    print("y =", y)

    if x == y:
        soter.stop()
        sys.exit("This is a crash.")

    soter.stop()
    soter.log("Stopped server.")