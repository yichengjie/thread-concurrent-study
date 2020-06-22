package com.yicj.study.threadpool;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示每个任务执行前后放钩子函数
 * ClassName: PauseableThreadPool
 * Description: TODO(描述)
 * Date: 2020/6/22 21:05
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class PauseableThreadPool extends ThreadPoolExecutor {
    private final ReentrantLock lock = new ReentrantLock();
    private Condition pauseCondition = lock.newCondition();
    private boolean isPaused;

    public PauseableThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    // 每个任务执行前回调
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        lock.lock();
        try {
            while (isPaused){
                pauseCondition.await();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void pause(){
        lock.lock();
        try {
            isPaused = false;
        }finally {
            lock.unlock();
        }
    }

    public void resume(){
        lock.lock();
        try {
            isPaused = false ;
            pauseCondition.signalAll();
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException{

        PauseableThreadPool pauseableThreadPool = new PauseableThreadPool(10, 20, 10l,
                TimeUnit.SECONDS, new LinkedBlockingQueue());

        Runnable runnable = () -> {
            System.out.println("我被执行");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };


        for (int i = 0; i < 10000; i++) {
            pauseableThreadPool.execute(runnable);
        }
        Thread.sleep(1500);
        pauseableThreadPool.pause();
        System.out.println("线程池被暂停了");
        Thread.sleep(1500);
        pauseableThreadPool.resume();
        System.out.println("线程池被恢复了");

    }

}
