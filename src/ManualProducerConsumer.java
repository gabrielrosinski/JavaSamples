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

        public void produce() throws InterruptedException {
            synchronized (this) {
                System.out.println("Producer thread running ..");
                wait();
                System.out.printf("Resumed");
            }
        }

        public void consume() throws  InterruptedException {
            Scanner scanner = new Scanner(System.in);
            Thread.sleep(2000);
            synchronized (this){
                System.out.println("Waiting for return key");
                scanner.nextLine();
                System.out.println("return key pressed");
                notify();
            }
        }
    }


}
