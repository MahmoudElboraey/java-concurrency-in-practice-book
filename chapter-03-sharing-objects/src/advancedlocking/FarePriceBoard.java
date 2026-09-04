package advancedlocking;

import java.util.Map;

import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FarePriceBoard {

    private static volatile int busyCount = 0;

    public static void main(String[] args) throws InterruptedException {
        PriceBoard board = new PriceBoard();
        java.util.Random random = new java.util.Random();

        for (int i = 0; i < 10_000; i++) {
            board.updateFare(random.nextInt(1000), 1 + random.nextInt(5));
        }

        Thread writer = new Thread(() -> {
            while (true) {
                board.updateFare(random.nextInt(1000), random.nextInt(6));
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        });
        writer.setDaemon(true);
        writer.start();

        Thread dashboard = new Thread(() -> {
            while (true) {
                if ("Busy".equals(board.snapshotOrSkip())) {
                    busyCount++;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        });
        dashboard.setDaemon(true);
        dashboard.start();

        java.util.List<Thread> readers = new java.util.ArrayList<>();
        for (int r = 0; r < 5; r++) {
            readers.add(new Thread(() -> {
                for (int i = 0; i < 50_000; i++) {
                    int hi = random.nextInt(1000);
                    int lo = hi > 0 ? random.nextInt(hi) : 0;
                    board.seatsInRange(lo, hi);
                }
            }));
        }

        long start = System.currentTimeMillis();
        for (Thread reader : readers) reader.start();
        for (Thread reader : readers) reader.join();
        long elapsed = System.currentTimeMillis() - start;

        System.out.println("5 readers x 50k range queries took " + elapsed + " ms");
        System.out.println("dashboard BUSY count: " + busyCount);

        Thread interruptibleWorker = new Thread(() -> {
            try {
                board.writeLock.lockInterruptibly();
                try {
                    Thread.sleep(3000);
                } finally {
                    board.writeLock.unlock();
                }
                System.out.println("worker finished uninterrupted");
            } catch (InterruptedException e) {
                System.out.println("interrupted, released");
            }
        });
        interruptibleWorker.start();
        Thread.sleep(1000);
        interruptibleWorker.interrupt();
        interruptibleWorker.join();
    }


    private static class PriceBoard {
        private volatile TreeMap<Integer , Integer> priceToSeats; // price --> seats available at this price
        // i used volatile so when publishing the map i ensure that is fully built
        private ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();
        private Lock readLock = rwlock.readLock();
        private Lock writeLock = rwlock.writeLock();

        public PriceBoard() {
            priceToSeats = new TreeMap<>();

        }

        public void updateFare (int price , int seats){
            writeLock.lock();
            try {
               if (seats == 0) priceToSeats.remove(price);
               else priceToSeats.put(price , seats);
            }finally {
                writeLock.unlock();
            }

        }


        public int seatsInRange (int low , int high){
            readLock.lock();
            try{

                Integer fromKey = priceToSeats.ceilingKey(low);
                Integer toKey = priceToSeats.floorKey(high);


                java.util.NavigableMap<Integer , Integer>  view = priceToSeats.subMap(fromKey , true , toKey , true);

                int answer = 0;

                for (Map.Entry<Integer , Integer> entry : view.entrySet()) {
                    answer += entry.getValue();
                }

                return answer;


            }finally {
                readLock.unlock();
            }
        }

        public String snapshotOrSkip(){
            if (readLock.tryLock()){
                try {
                    return priceToSeats.toString();
                }finally {
                    readLock.unlock();
                }
            }else {
                return "Busy";
            }
        }
    }
}
