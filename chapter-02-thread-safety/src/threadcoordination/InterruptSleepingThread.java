package threadcoordination;


public class InterruptSleepingThread {

    public static void main (String[] args) {

        Thread thread = new Thread(new SleepingThread());
        thread.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
        thread.interrupt();

    }


    private static class SleepingThread implements Runnable{
        @Override
        public void run() {
            long time = 10 * 60 * (int)1e3;
            try {
                System.out.println("staring download");
                Thread.sleep(time);
            }catch (InterruptedException e){
                System.out.println("download cancelled");
                Thread.currentThread().interrupt(); // restore the interrupt flag back
            }

        }


    }
}
