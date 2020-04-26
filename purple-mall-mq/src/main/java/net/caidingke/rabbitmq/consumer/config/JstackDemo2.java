package net.caidingke.rabbitmq.consumer.config;

public class JstackDemo2 {
    public static void main(String[] args){
        Thread thread1 = new Thread(new DeadLockClass(true));
        Thread thread2 = new Thread((new DeadLockClass(false)));
        thread1.start();
        thread2.start();
    }
}
class DeadLockClass implements Runnable {
    public boolean flag;
    DeadLockClass(boolean flag) {
        this.flag = flag;
    }
    @Override
    public void run() {
        if (flag) {
            while (true) {
                synchronized (Suo.o1) {
                    System.out.println("o1" + Thread.currentThread().getName());
                    synchronized (Suo.o2) {
                        System.out.println("o2" + Thread.currentThread().getName());
                    }
                }
            }
        } else {
            while (true) {
                synchronized (Suo.o2) {
                    System.out.println("o2" + Thread.currentThread().getName());
                    synchronized (Suo.o1) {
                        System.out.println("o1" + Thread.currentThread().getName());
                    }
                }
            }
        }
    }
}
class Suo {
    static Object o1 = new Object();
    static Object o2 = new Object();
}
