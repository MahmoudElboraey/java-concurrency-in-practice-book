package bookexamples;
public class UnsafeSenario {
    public static void main(String[] args) throws InterruptedException {

        UnsafeSequence unsafeSequence = new UnsafeSequence();

        Thread t1 = new Thread (new Runnable(){
            @Override
            public void run(){
                String threadName = Thread.currentThread().getName();
                System.out.println("im here right now in thread " + threadName +
                        "and this thread priority is "+ Thread.currentThread().getPriority());
                int loop = 1000000;

                for (int i = 1; i <= loop; i++){
                    System.out.println("thread name is " + threadName + " value is " + unsafeSequence.getNext());
                }
            }
        });

       t1.setName("worker one");


        Thread t2 = new Thread (new Runnable(){
            @Override
            public void run(){
                String threadName = Thread.currentThread().getName();
                System.out.println("im here right now in thread " + threadName +
                "and this thread priority is "+ Thread.currentThread().getPriority());
                int loop = 1000000;

                for (int i = 1; i <= loop; i++){
                    System.out.println("thread name is " + threadName + " value is " + unsafeSequence.getNext());
                }
            }
        });

        t2.setName("worker two");
        System.out.println("current thread is befroe starting the threads " + Thread.currentThread().getName());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("current thread is after starting the threads " + Thread.currentThread().getName());

        System.out.println("current thread is befroe ending the threads " + Thread.currentThread().getName() + " value is " + unsafeSequence.getNext());









    }
}