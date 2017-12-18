package reentrantlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by sh on 2017/12/10.
 */
public class ReentrantLockCondition implements Runnable {

    public static final ReentrantLock lock = new ReentrantLock();

    public static final Condition condition = lock.newCondition();

    @Override
    public void run() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " pause");
            long time = condition.awaitNanos(2000000000);
            System.out.println(time);
            System.out.println(Thread.currentThread().getName() + " is going on");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockCondition demo = new ReentrantLockCondition();
        Thread t1 = new Thread(demo, "thread-1");
        t1.start();
        Thread.sleep(2000);
        lock.lock();
        condition.signal();
        lock.unlock();
    }
}
