package reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by sh on 2017/12/10.
 */
public class FairLock implements Runnable {

    public static final ReentrantLock fairLock = new ReentrantLock(true);

    @Override
    public void run() {
        while (true) {
            try {
                fairLock.lock();
                System.out.println(Thread.currentThread().getName() + " get lock");
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (fairLock.isHeldByCurrentThread()) {
                    fairLock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) {
        FairLock demo = new FairLock();
        Thread t1 = new Thread(demo);
        Thread t2 = new Thread(demo);
        t1.start();
        t2.start();
    }
}