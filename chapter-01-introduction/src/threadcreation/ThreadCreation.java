package threadcreation;

public class ThreadCreation {

    public static void main(String[] args) {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread name is " + Thread.currentThread().getName() + " and has a" +
                        "priority " + Thread.currentThread().getPriority());
                throw new RuntimeException("this is a runtime exception");

            }
        }
        );
        t1.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread t , Throwable e){
                System.out.println("the exception is handdled " + e.getMessage());
            }
        });


        System.out.println("current thread is " + Thread.currentThread().getName());
        t1.setName("new worker thread");
        t1.setPriority(Thread.NORM_PRIORITY);
        t1.start();
        System.out.println("current thread is " + Thread.currentThread().getName());


    }
}
