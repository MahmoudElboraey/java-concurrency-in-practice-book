package threadcoordination;


import java.math.BigInteger;

public class InterruptCpuBound {

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(new CpuTask(new BigInteger("20000000000")));
        thread.start();
        thread.join(1000);

        thread.interrupt();

    }

    private static class CpuTask implements Runnable{

        final private BigInteger value;
        private BigInteger result = BigInteger.ZERO;
        public CpuTask(BigInteger value){
            this.value = value;
        }


        @Override
        public void run() {
            for (BigInteger i = BigInteger.ONE; i.compareTo(value) <= 0; i = i.add(BigInteger.ONE)){
                if (Thread.currentThread().isInterrupted()){
                    System.out.println("interrupted while waiting oo ");
                    return;
                }
                 BigInteger mul = i.multiply(i);
                result = result.add(mul);
            }
        }

        public BigInteger getResult(){
            return result;
        }
    }
}
