#!/usr/bin/env python3

import sys
import grpc
import time
import threading

from concurrent import futures

from soter import Soter, SoterThread

global soter
soter = Soter()

global counter
counter = 0

if __name__ == "__main__":
    def thread_function(id):
        soter.log("Thread " + str(id) + " starting.")

        # Read global.
        global counter
        tmp = counter

        # Force context switch.
        time.sleep(1)
        soter.yield_task(id)

        # Write global
        counter = tmp + 1

        soter.log("Thread " + str(id) + " finishing.")

    start_time = time.time()

    threads = []

    for x in range(1, 5):
        x = SoterThread(target=thread_function, args=(x,))
        threads.append(x)

    soter.log("Starting threads... ---------------------------------------------------")
    for x in threads:
        x.start()

    soter.log("Joining threads...  ---------------------------------------------------")
    for x in threads:
        x.join()

    soter.log("Finished.")

    end_time = time.time()
    diff_time = (end_time - start_time)
    soter.log("Program execution took (time.time): " + str(diff_time))
    soter.log("Final value of counter: " + str(counter))

    if counter != len(threads):
        soter.log("****************** FINAL VALUE WAS WRONG: " + str(counter))
        soter.stop()
        sys.exit("We got the wrong value!")
    else:
        soter.stop()