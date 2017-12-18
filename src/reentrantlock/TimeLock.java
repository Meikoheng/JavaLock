package reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by sh on 2017/12/10.
 */
public class TimeLock implements Runnable {

    public static final ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            if (lock.tryLock(1, TimeUnit.SECONDS)) {
                Thread.sleep(3000);
                System.out.println(Thread.currentThread().getName() + " complete");
            } else {
                System.out.println(Thread.currentThread().getName() + " get lock failed");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TimeLock demo = new TimeLock();
        Thread t1 = new Thread(demo, "thread-1");
        Thread t2 = new Thread(demo, "thread-2");
        t1.start();
        t2.start();
    }
}
