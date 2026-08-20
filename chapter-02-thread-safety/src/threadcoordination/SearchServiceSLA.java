package threadcoordination;


import java.util.ArrayList;
import java.util.List;

public class SearchServiceSLA {

    public static void main(String[] args) {

        ConnectorTask amadeus = new ConnectorTask(1000);
        ConnectorTask airarabia = new ConnectorTask(300);
        ConnectorTask aegen = new ConnectorTask(4000);
        List<ConnectorTask> connectors = new ArrayList<ConnectorTask>();
        connectors.add(amadeus);
        connectors.add(airarabia);
        connectors.add(aegen);
        for (ConnectorTask task : connectors) {
            task.setDaemon(true);
        }

        for (Thread t : connectors) {
            t.start();
        }

        long total = 2000;
        for (Thread t : connectors) {

            long currentTime = System.currentTimeMillis();
            try {
                t.join(total);
            } catch (InterruptedException e) {

            }
            long spent = System.currentTimeMillis() - currentTime;
            long remain = total - spent;
            if (remain <= 0) break;
            total = remain;
        }

        for (ConnectorTask task : connectors) {
            task.interrupt();
            String state = task.getFinished();
            System.out.println("thread  " + task.getName() + " is " + state);

        }


    }

    private static class ConnectorTask extends Thread {
        private long numberOfSeconds;
        private String finished;
        public ConnectorTask(long numberOfSeconds) {
            this.numberOfSeconds = numberOfSeconds;
            this.finished = "cancelled";
        }

        @Override
        public void run() {
            try {
                Thread.sleep(numberOfSeconds);
                finished = "finished";
            }catch (InterruptedException e){
                System.out.println("unfinished yet , cancelled");
                Thread.currentThread().interrupt();
            }

        }

        public String getFinished() {
            return finished;
        }

    }
}
