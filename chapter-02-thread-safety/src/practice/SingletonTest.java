package practice;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SingletonTest {

    public static void main(String[] args) throws InterruptedException {
        int threads = 8;

        Set<Integer> unsafeHashes = ConcurrentHashMap.newKeySet();
        runRace(threads, () -> unsafeHashes.add(System.identityHashCode(UnsafeSingleton.getInstance())));
        System.out.println("UNSAFE distinct instances: " + unsafeHashes.size() + " " + unsafeHashes);

        Set<Integer> safeHashes = ConcurrentHashMap.newKeySet();
        runRace(threads, () -> safeHashes.add(System.identityHashCode(SafeSingleton.getInstance())));
        System.out.println("SAFE   distinct instances: " + safeHashes.size() + " " + safeHashes);
    }

    private static void runRace(int threads, Runnable task) throws InterruptedException {
        CountDownLatch startGate = new CountDownLatch(1);
        CountDownLatch endGate = new CountDownLatch(threads);
        for (int i = 0; i < threads; i++) {
            new Thread(() -> {
                try {
                    startGate.await();      // everyone parks here
                    task.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    endGate.countDown();
                }
            }).start();
        }
        startGate.countDown();              // fire all threads at once
        endGate.await();
    }


    private static class UnsafeSingleton{
        private static UnsafeSingleton instance;

        private UnsafeSingleton(){

        }

        public static UnsafeSingleton getInstance(){
            if(instance == null){
                instance = new UnsafeSingleton();
            }
            return instance;
        }
    }


    private static class SafeSingleton{
        private static volatile SafeSingleton instance;
        // volatile stops JIT publishing the reference before the constructor finishes , check 1 see non null returns half built object
        private static final Lock lockObject = new ReentrantLock();

        private SafeSingleton(){

        }

        public static SafeSingleton getInstance(){
            if(instance == null){
                lockObject.lock();
                try{
                    if (instance == null){
                        instance = new SafeSingleton();
                    }

                }finally {
                    lockObject.unlock();
                }
            }

            return instance;
        }
    }
}
