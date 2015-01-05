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
    public static final long DEFAULT_DELAY = 20; // ms
    public static final long DEFAULT_UPDATES = 1; // updates / MB

    public MemEater(long delay, long updates) {
        System.out.println(String.format(
                "Allocating full memory for max heap %d MB",
                Runtime.getRuntime().maxMemory()/1048576));
        List<long[]> blocks = new ArrayList<>();
        try {
            //noinspection InfiniteLoopStatement
            while (true) {
                blocks.add(new long[1048576 / 8]); // 1MB
            }
        } catch (OutOfMemoryError e) {
            System.out.println(String.format(
                    "Allocated %d MB. Switching to %d random updates per MB every %s ms",
                    blocks.size(), delay, updates));
        }

        Random random = new Random();
        while (true) {
            for (long[] block: blocks) {
                for (long update = 0 ; update < updates ; update++) {
                    block[((int) (random.nextDouble() * block.length))]++;
                }
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
        new MemEater(args.length > 0 ? Long.parseLong(args[0]) : DEFAULT_DELAY,
                     args.length > 1 ? Long.parseLong(args[1]) : DEFAULT_UPDATES);
    }
}