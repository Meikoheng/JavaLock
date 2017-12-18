package reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by sh on 2017/12/10.
 */
public class InterruptLockDemo implements Runnable {

    public static final ReentrantLock lock1 = new ReentrantLock();
    public static final ReentrantLock lock2 = new ReentrantLock();
    int lock;

    public InterruptLockDemo(int lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            if (lock == 1) {
                lock1.lockInterruptibly();
                Thread.sleep(500);
                lock2.lockInterruptibly();
            } else {
                lock2.lockInterruptibly();
                Thread.sleep(500);
                lock1.lockInterruptibly();
            }
            System.out.println(Thread.currentThread().getName() + " complete");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock1.isHeldByCurrentThread()) {
                lock1.unlock();
            }
            if (lock2.isHeldByCurrentThread()) {
                lock2.unlock();
            }
            System.out.println(Thread.currentThread().getName() + " end");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        InterruptLockDemo demo1 = new InterruptLockDemo(1);
        InterruptLockDemo demo2 = new InterruptLockDemo(2);
        Thread t1 = new Thread(demo1, "thread-1");
        Thread t2 = new Thread(demo2, "thread-2");
        t1.start();
        t2.start();
        Thread.sleep(1000);
        t2.interrupt();
    }
}
