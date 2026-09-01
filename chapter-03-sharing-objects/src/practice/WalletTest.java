package practice;


/*
im gonna explain every thing i will do in this class , so i can make sure i understand eveything i read
 */
public class WalletTest {



    public static void main (String[] args) {
        Wallet a = new Wallet(100000);
        Wallet b = new Wallet(100000);

        final int times = 10000;
        Thread thread1=  new Thread(() -> {
            for (int i = 0; i < times; i++) {
                Wallet.transfer(a , b , 10);
            }
        });


        Thread thread2=  new Thread(() -> {
            for (int i = 0; i < times; i++) {
                Wallet.transfer(b , a , 10);
            }
        });



        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        }catch (InterruptedException e){

        }

        System.out.println(a.balance + "      " + b.balance);


    }

    private static class Wallet {

        /*
        why i make volatile -> to avoid non atomic 64 non-atomic numeric operations for setting the value
        volatile ensures memory visibility & atomic for 64 bit (long , double)
         */
        private volatile long balance;
        private  static volatile long maxTransferSeen;

        public Wallet(long balance) {
            this.balance = balance;
        }

        public synchronized void deposit(long value) {
        /*
        why i make volatile because the increment operation is not a single operation (read , add , write)
        i wanna encapsualte these operations and make sure they will be executed by one thread at a time , because balance is shared mutable state
         */
            this.balance += value;
        }

        public synchronized void withdraw(long value) {
            this.balance -= value;
        }


        public long getBalance() { // i don't need to do the lock here , because volatile give me the visibility
            return balance;
        }

        public synchronized long getMaxTransferSeen() {
            return maxTransferSeen;
        }

//        public static void transfer (Wallet from , Wallet to , long amount) {
//            synchronized (from) {
//                synchronized (to) {
//                    System.out.println("inside the transfer");
//                    maxTransferSeen = Math.max(maxTransferSeen , amount);
//                    from.withdraw(amount);
//                    to.deposit(amount);
//                }
//            }  this version of code will cause a dead lock


        public static void transfer(Wallet from, Wallet to, long amount) {
            Wallet first = System.identityHashCode(from) <= System.identityHashCode(to) ? from : to;
            Wallet second = (first == from) ? to : from;
            synchronized (first) {
                synchronized (second) {
                    maxTransferSeen = Math.max(maxTransferSeen, amount);
                    from.withdraw(amount);
                    to.deposit(amount);
                }
            }
        }
    }





}
