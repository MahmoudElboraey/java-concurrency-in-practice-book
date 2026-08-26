package codeexamples;

public class NoVisibility {

    private static boolean ready;
    private static int number;


    public static void main(String[] args) {
        new ReaderThread().start();
        number = 42;
        ready = true;

    }


    public static class ReaderThread extends Thread {
        public void run (){
            while (!ready) {
                Thread.yield();
            }
            System.out.println(number);
        }
    }
}
