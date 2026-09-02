package practice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class RaceConditionTest {

    public static void main(String[] args) throws InterruptedException {
        int trials = 2000;
        int threads = 4;

        int unsafeDuplicates = 0;
        for (int t = 0; t < trials; t++) {
            AtomicInteger processed = new AtomicInteger();
            UnsafeRaceCondition unsafe = new UnsafeRaceCondition(processed);
            race(threads, () -> unsafe.addID(7));
            if (processed.get() > 1) unsafeDuplicates++;   // id 7 processed more than once = bug
        }
        System.out.println("UNSAFE: duplicate processing in " + unsafeDuplicates + " of " + trials + " trials");

        int safeDuplicates = 0;
        for (int t = 0; t < trials; t++) {
            AtomicInteger processed = new AtomicInteger();
            RaceCondition safe = new RaceCondition(processed);
            race(threads, () -> safe.addID(7));
            if (processed.get() > 1) safeDuplicates++;
        }
        System.out.println("SAFE:   duplicate processing in " + safeDuplicates + " of " + trials + " trials");
    }

    private static void race(int threads, Runnable task) throws InterruptedException {
        CountDownLatch startGate = new CountDownLatch(1);
        CountDownLatch endGate = new CountDownLatch(threads);
        for (int i = 0; i < threads; i++) {
            new Thread(() -> {
                try {
                    startGate.await();
                    task.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    endGate.countDown();
                }
            }).start();
        }
        startGate.countDown();
        endGate.await();
    }

    private static class UnsafeRaceCondition {
        private final List<Integer> seen = new ArrayList<>();
        private final AtomicInteger processed;

        public UnsafeRaceCondition(AtomicInteger processed) {
            this.processed = processed;
        }

        public void addID(Integer id) {
            if (!seen.contains(id)) {
                seen.add(id);
                process(id);
            }
        }

        private void process(Integer id) {
            processed.incrementAndGet();
        }
    }

    private static class RaceCondition { // this check-then-act race condtion pattern
        private final List<Integer> seen;
        private final AtomicInteger processed;

        public RaceCondition(AtomicInteger processed) {
            this.processed = processed;
            seen = new ArrayList<>();
        }

        public void addID(Integer id){ // i need to remove the long running computation from the synchronization block
            boolean firstTime = false;

            synchronized (this){
               firstTime = !seen.contains(id);
               if (firstTime){
                   seen.add(id);
               }
            }
            if (firstTime){
                process(id);
            }
        }

        private void process(Integer id){
            processed.incrementAndGet();
        }
    }
}
