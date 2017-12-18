package reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author sh
 * @date 2017/12/10
 */
public class ReenterLock implements Runnable {

    public static final ReentrantLock lock = new ReentrantLock();

    public static int i = 0;

    @Override
    public void run() {
        for (int j = 0; j < 1000000; j++) {
            lock.lock();
            i++;
            lock.unlock();
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReenterLock demo = new ReenterLock();
        Thread t1 = new Thread(demo);
        Thread t2 = new Thread(demo);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
