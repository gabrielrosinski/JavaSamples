import java.security.PrivateKey;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class ManualProducerConsumer {


    public void main(String[] args) throws InterruptedException{
        final Processor processor = new Processor();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    processor.produce();
                }catch(InterruptedException e){
                    System.out.println(e);
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    processor.consume();
                }catch(InterruptedException e){
                    System.out.println(e);
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t1.join();

    }


    public class Processor {

        private LinkedList<Integer> list = new LinkedList<Integer>();
        private final int LIMIT = 10;
        private Object lock = new Object();

        public void produce() throws InterruptedException {
            int value = 0;

            synchronized (this) {
                while (true) {
                    synchronized (lock) {
                        while (list.size() == LIMIT) {
                            lock.wait();
                        }
                        list.add(value++);
                        lock.notify();
                    }
                }
            }
        }

        public void consume() throws  InterruptedException {

            Random random = new Random();

            while (true) {
                synchronized (lock){
                    while(list.size() == 0) {
                        lock.wait();
                    }
                    System.out.println("List size is: " + list.size());
                    int value = list.removeFirst();
                    System.out.println("; value is: " + value);
                    lock.notify();
                }
                Thread.sleep(random.nextInt(1000));
            }
        }
    }


}
