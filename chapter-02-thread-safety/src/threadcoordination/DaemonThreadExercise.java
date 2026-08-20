package threadcoordination;

public class DaemonThreadExercise {
    /*
    without daemon flag the program will not stop running
    because the jvm can't exit if there is undaemon thread still running
     */
    public static void main(String[] args){
        Thread thread = new DaemonTask();
        thread.setDaemon(true);
        thread.start();

        try {
            Thread.sleep(3000);
        }catch (InterruptedException e){
            System.out.println("interrupted main thread");
        }


    }


    private static class DaemonTask extends Thread{

        @Override
        public void run(){
            while(true){
                try{
                    Thread.sleep(500);
                }catch(InterruptedException e){
                    System.out.println("interrupted daemon thread");
                }
                System.out.println("heartbeat");
            }
        }
    }
}
