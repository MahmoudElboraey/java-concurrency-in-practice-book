package threadcoordination;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class FactorialTask {
    public static void main(String[] args){
        List<FactorialCalculator> threads = new ArrayList<>();
        threads.add(new FactorialCalculator(new BigInteger("200")));
        threads.add(new FactorialCalculator(new BigInteger("1000000000")));
        threads.add(new FactorialCalculator(new BigInteger("2000000000")));
        threads.add(new FactorialCalculator(new BigInteger("300")));
        threads.add(new FactorialCalculator(new BigInteger("40000")));
        for (Thread thread : threads) {
            thread.setDaemon(true);
        }

        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join(2000);
            } catch (InterruptedException e) {
                System.out.println("Interrupted " + thread.getName());
            }
        }

        for (FactorialCalculator thread : threads) {
            thread.interrupt();
            if (thread.getIsFinished()) {
                System.out.println("Thread " + thread.getName() + " is finished  and result is " + thread.getIsFinished());
            }
        }


    }

    private static class FactorialCalculator extends Thread{

        private BigInteger result = BigInteger.ONE;
        private BigInteger number;
        private Boolean isFinished = false; // visibility issue for unfinished threads
        public FactorialCalculator(BigInteger number){
            this.number = number;
        }
        @Override
        public void run() {
            for (BigInteger i = BigInteger.ONE; i.compareTo(number) <= 0; i = i.add(BigInteger.ONE)){
                if (Thread.currentThread().isInterrupted()){
                    isFinished = false;
                    System.out.println("Thread " + Thread.currentThread().getName() + " is interrupted aand still in progress");
                    return;
                }
                result = result.multiply(i);
            }
            isFinished = true;
        }

        public BigInteger getResult(){
            return result;
        }
        public boolean getIsFinished(){
            return isFinished;
        }
    }
}
