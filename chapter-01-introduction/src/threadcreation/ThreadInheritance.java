package threadcreation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ThreadInheritance {

    private static final int range = (int)1e5;
    public static void main(String[] args) {

        Random rand = new Random();
        Valut valut = new Valut(rand.nextInt(range));

        List<Thread> threads = new ArrayList<Thread>();
        threads.add(new AscendingHackerThread(valut));
        threads.add(new DescendingHackerThread(valut));
        threads.add(new PoliceThread());

        for (Thread thread : threads) {
            thread.start();
        }


    }


    private static class Valut {
        private final int password;

        public Valut(int password){
            this.password = password;
        }

        public boolean isCorrectPassword(int password){
            try {
                Thread.sleep(5);
            }catch (InterruptedException e){

            }
            return this.password == password;
        }


    }

    private abstract static class HackerThread extends Thread{
        protected final Valut valut;
        public HackerThread(Valut valut){
            this.valut = valut;
            this.setPriority(Thread.MAX_PRIORITY);
            this.setName(this.getClass().getSimpleName());
        }

        @Override
        public void start(){
            System.out.println("start thread is " + this.getName());
            super.start();
        }
    }

    private static class AscendingHackerThread extends HackerThread{

        public AscendingHackerThread(Valut valut){
            super(valut);
        }

        @Override
        public void run(){
            for (int i = 0; i < range ; ++i){
                System.out.println(this.getName() + " value is " + i);
                if (valut.isCorrectPassword(i)){
                    System.out.println(this.getName() + " is the winner");
                    System.exit(0);
                }
            }
        }
    }

    private static class DescendingHackerThread extends HackerThread {
        public DescendingHackerThread(Valut valut){
            super(valut);
        }

        @Override
        public void run(){
            for (int i = range -1; i >= 0; --i){
                System.out.println(this.getName() + " value is " + i);
                if (valut.isCorrectPassword(i)){
                    System.out.println(this.getName() + " is the winner");
                    System.exit(0);
                }
            }
        }
    }

    private static class PoliceThread extends Thread{

        @Override
        public void run(){
            for (int i = 0; i < 10; ++i){
                try {
                    sleep(1000);

                }catch (InterruptedException e){

                }
                System.out.println(this.getName() + " value is " + i);
            }
            System.out.println("game over for u hackers");
            System.exit(0);
        }

    }




}
