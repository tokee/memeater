package dk.ekot.memeater;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Allocated memory in blocks of 1MB until an OutOfMemoryException is thrown.
 * After OOM, no further memory is allocated, but the content is changed at
 * random to awoid the memory being swapped out.
 */
public class MemEater {
    public static final long DELAY = 20; // ms

    public MemEater(long delay) {
        List<long[]> blocks = new ArrayList<>();
        try {
            //noinspection InfiniteLoopStatement
            while (true) {
                blocks.add(new long[1048576 / 8]); // 1MB
            }
        } catch (OutOfMemoryError e) {
            System.out.println("Allocated " + blocks.size() + "MB. " +
                               "Switching to active mode with random updates every " + DELAY + "ms.");
        }

        Random random = new Random();
        while (true) {
            for (long[] block: blocks) {
                block[((int) (random.nextDouble() * block.length))]++;
            }
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                System.err.println("Interrupted while waiting for next update");
                return;
            }
        }
    }

    public static void main(String[] args) {
        new MemEater(DELAY);
    }
}