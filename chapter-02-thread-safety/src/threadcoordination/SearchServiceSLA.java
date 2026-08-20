package threadcoordination;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SearchServiceSLA {

    public static void main(String[] args) {

        List<ConnectorTask> tasks = new ArrayList<>();
        tasks.add(new ConnectorTask("amadeus", 1000));
        tasks.add(new ConnectorTask("airarabia", 300));
        tasks.add(new ConnectorTask("aegean", 4000));

        List<Thread> threads = new ArrayList<>();
        for (ConnectorTask task : tasks) {
            Thread t = new Thread(task);
            t.setDaemon(true);
            threads.add(t);
        }

        for (Thread t : threads) {
            t.start();
        }

        long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(2);
        for (Thread t : threads) {
            long remainingMs = TimeUnit.NANOSECONDS.toMillis(deadline - System.nanoTime());
            if (remainingMs <= 0) break;
            try {
                t.join(remainingMs);
            } catch (InterruptedException e) {
                System.out.println("main interrupted while waiting for connectors");
                Thread.currentThread().interrupt();
            }
        }

        List<String> results = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            ConnectorTask task = tasks.get(i);
            if (task.isFinished()) {
                results.add(task.getResult());
            } else {
                threads.get(i).interrupt();
                System.out.println(task.getName() + " missed the SLA, cancelled");
            }
        }

        System.out.println("collected results: " + results);
    }

    private static class ConnectorTask implements Runnable {

        private final String name;
        private final long sleepMillis;
        private boolean finished = false;
        private String result;

        ConnectorTask(String name, long sleepMillis) {
            this.name = name;
            this.sleepMillis = sleepMillis;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(sleepMillis);
                result = name + ": 12 offers";
                finished = true;
            } catch (InterruptedException e) {
                System.out.println(name + " cancelled");
                Thread.currentThread().interrupt();
            }
        }

        public boolean isFinished() {
            return finished;
        }

        public String getResult() {
            return result;
        }

        public String getName() {
            return name;
        }
    }
}
