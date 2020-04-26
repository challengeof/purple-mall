package net.caidingke.rabbitmq.consumer.config;

public class JstackDemo1 {
    public static void main(String[] args){
        Thread thread = new Thread(new Thread1());
        thread.start();
    }
}
class Thread1 extends Thread {
    @Override
    public void run(){
        while (true) {
            System.out.println(1);
        }
    }
}
