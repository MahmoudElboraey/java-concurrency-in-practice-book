package threadcoordination;

import java.math.BigInteger;

public class ComplexCalculation {

    public static void main(String[] args) {
        ComplexCalculation calculation = new ComplexCalculation();
        BigInteger result = calculation.calculateResult(
                BigInteger.valueOf(2), BigInteger.valueOf(10),
                BigInteger.valueOf(3), BigInteger.valueOf(5));
        System.out.println(result); // 2^10 + 3^5 = 1024 + 243 = 1267
    }

    public BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2) {
        BigInteger result;
        /*
            Calculate result = ( base1 ^ power1 ) + (base2 ^ power2).
            Where each calculation in (..) is calculated on a different thread
        */


        PowerCalculatingThread thread1 = new PowerCalculatingThread(base1 , power1);
        PowerCalculatingThread thread2 = new PowerCalculatingThread(base2 , power2);
        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        }catch (InterruptedException e){

        }

        BigInteger r1 = thread1.getResult();
        BigInteger r2 = thread2.getResult();
        result = r1.add(r2);

        return result;
    }

    private static class PowerCalculatingThread extends Thread {
        private BigInteger result = BigInteger.ONE;
        private BigInteger base;
        private BigInteger power;

        public PowerCalculatingThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
           /*
           Implement the calculation of result = base ^ power
           */
            for (BigInteger i = power ; i.compareTo(BigInteger.ZERO) != 0;  i = i.subtract(BigInteger.ONE) ) {
                result = result.multiply(base);
            }

        }

        public BigInteger getResult() { return result; }

    }
}
