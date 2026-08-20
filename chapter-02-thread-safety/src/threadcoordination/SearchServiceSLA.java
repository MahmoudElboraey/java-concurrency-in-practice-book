package threadcoordination;


import java.util.ArrayList;
import java.util.List;

public class SearchServiceSLA {

    public static void main(String[] args) {

        Thread amadeus = new Thread(new connectorTask(1000));
        Thread airarabia = new Thread(new connectorTask(3000));
        Thread aegen = new Thread(new connectorTask(4000));
        List<Thread> connectors = new ArrayList<Thread>();
        connectors.add(amadeus);
        connectors.add(airarabia);
        connectors.add(aegen);

        for (Thread t : connectors) {
            t.start();
        }

        for (Thread t : connectors) {
            try {
                t.join(2000);
            } catch (InterruptedException e) {

            }
        }


    }

    private static class connectorTask implements Runnable {
        private long numberOfSeconds;
        private String finished;
        public connectorTask(long numberOfSeconds) {
            this.numberOfSeconds = numberOfSeconds;
            this.finished = "cancelled";
        }

        @Override
        public void run() {
            try {
                Thread.sleep(numberOfSeconds);
            }catch (InterruptedException e){
                System.out.println("unfinished yet , cancelled");
            }
            finished = "finished";
        }

        public String getFinished() {
            return finished;
        }

    }
}
