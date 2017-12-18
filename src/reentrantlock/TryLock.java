package reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by sh on 2017/12/10.
 */
public class TryLock implements Runnable {

    public static final ReentrantLock lock1 = new ReentrantLock();
    public static final ReentrantLock lock2 = new ReentrantLock();
    int lock;

    public TryLock(int lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        if (lock == 1) {
            while (true) {
                try {
                    if (lock1.tryLock()) {
                        try {
                            Thread.sleep(500);
                            if (lock2.tryLock()) {
                                System.out.println(Thread.currentThread().getName() + " complete");
                                return;
                            }
                        } catch (InterruptedException e) {
                        } finally {
                            if (lock2.isHeldByCurrentThread()) {
                                lock2.unlock();
                            }
                        }
                    }
                } finally {
                    if (lock1.isHeldByCurrentThread()) {
                        lock1.unlock();
                    }
                }
            }
        } else {
            while (true) {
                try {
                    if (lock2.tryLock()) {
                        try {
                            Thread.sleep(500);
                            if (lock1.tryLock()) {
                                System.out.println(Thread.currentThread().getName() + " complete");
                                return;
                            }
                        } catch (InterruptedException e) {
                        } finally {
                            if (lock1.isHeldByCurrentThread()) {
                                lock1.unlock();
                            }
                        }
                    }
                } finally {
                    if (lock2.isHeldByCurrentThread()) {
                        lock2.unlock();
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TryLock demo1 = new TryLock(1);
        TryLock demo2 = new TryLock(2);
        Thread t1 = new Thread(demo1, "thread-1");
        Thread t2 = new Thread(demo2, "thread-2");
        t1.start();
        t2.start();
    }
}
