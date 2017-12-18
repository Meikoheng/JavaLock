package reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by sh on 2017/12/10.
 */
public class MultiLockDemo implements Runnable {
    public static final ReentrantLock lock = new ReentrantLock();

    public static int i = 0;

    @Override
    public void run() {
        for (int j = 0; j < 1000000; j++) {
            lock.lock();
            lock.lock();
            i++;
            lock.unlock();
            lock.unlock();
        }
        System.out.println(Thread.currentThread().getName() + " end");
    }

    public static void main(String[] args) throws InterruptedException {
        MultiLockDemo demo = new MultiLockDemo();
        Thread t1 = new Thread(demo, "thread-1");
        Thread t2 = new Thread(demo, "thread-2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
