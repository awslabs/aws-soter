package com.amazon.soter.examples.boundedbuffer.harnesses;

import com.amazon.soter.examples.boundedbuffer.implementations.BoundedBufferWithLocksAndConditionsBroken;
import com.amazon.soter.examples.boundedbuffer.threads.Reader;
import com.amazon.soter.examples.boundedbuffer.threads.Writer;
import com.amazon.soter.java.util.concurrent.ForkJoinPool;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/** Identify configurations that are faulty using the broken bounded buffer implementation. */
public class FindConfigurationsInBrokenBufferHarness {
    public void awaitAll(ForkJoinPool customThreadPool) {
        // Required, otherwise the loop will not terminate as the thread pool is still accepting jobs.
        customThreadPool.shutdown();

        while (true) {
            try {
                if (customThreadPool.awaitTermination(1, TimeUnit.SECONDS)) break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void run() {
        Random random = new Random();

        // Generate random configuration while ensuring we don't explore configurations
        // where the program trivially deadlocks (such as zero readers, zero writers,
        // mismatch between total produced and consumed items etc)
        int bufferSize = random.nextInt(4) + 1;
        int readers = random.nextInt(4) + 1;
        int writers = random.nextInt(4) + 1;
        int iterations = random.nextInt(10) + 1;

        int totalIterations = iterations * readers;
        int writerIterations = totalIterations / writers;
        int remainder = totalIterations % writers;

        // Size the pool appropriately.
        ForkJoinPool customThreadPool = new ForkJoinPool(readers + writers);

        System.out.println("Testing buffer size=" + bufferSize + ", reader=" + readers
                + ", writer=" + writers + ", iterations=" + iterations);
        BoundedBufferWithLocksAndConditionsBroken buffer = new BoundedBufferWithLocksAndConditionsBroken(bufferSize);

        for (int i = 0; i < readers; i++) {
            customThreadPool.execute(new Reader(buffer, iterations));
        }

        int x = 0;

        for (int i = 0; i < writers; i++) {
            int w = writerIterations;

            if (i == writers - 1) {
                w += remainder;
            }
            x += w;
            customThreadPool.execute(new Writer(buffer, w));
        }

        awaitAll(customThreadPool);
    }
}
