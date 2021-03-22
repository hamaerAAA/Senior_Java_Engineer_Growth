package sleepYeild;

public class ThreadTest {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            int count=0;
            while (true){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("------>1  " + count++ );
            }
        }, "task1");
        Thread t2 = new Thread(() -> {
            int count=0;
            while (true){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               // Thread.yield();
                System.out.println("      ------>2  " + count++ );
            }
        }, "task2");

        t1.setPriority(Thread.MIN_PRIORITY);
        t2.setPriority(Thread.MAX_PRIORITY);
        t1.start();
        t2.start();
    }
}
