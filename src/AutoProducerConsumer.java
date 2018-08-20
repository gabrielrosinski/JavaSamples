import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class AutoProducerConsumer {

    //Concurent packeges handles synchronization
    private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    producer();
                }catch(InterruptedException e){
                    System.out.println(e);
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    consumer();
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

    private static void producer() throws  InterruptedException{
        Random random = new Random();

        while(true) {
            queue.put(random.nextInt(100));
        }
    }

    private static void consumer()throws  InterruptedException{
        Random random = new Random();

        while (true){
            Thread.sleep(100);
            if (random.nextInt(10) == 0) {
                Integer value = queue.take(); //take methode will wait idle until there is something to take
                System.out.println("Taken value: " + value + "; Queue size is: " + queue.size());
            }
        }
    }
}
