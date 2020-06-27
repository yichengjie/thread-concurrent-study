package com.yicj.study.cas;

/**
 * ClassName: SimulateCAS
 * Description: TODO(描述)
 * Date: 2020/6/26 21:01
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class TwoThreadCompettion implements Runnable {

    private volatile int value ;

    public synchronized int compareAndSwap(int expectedValue, int newValue){
        int oldValue = value ;
        if (expectedValue == oldValue){
            value = newValue ;
        }
        return oldValue ;
    }

    public static void main(String[] args) throws InterruptedException {
        TwoThreadCompettion r = new TwoThreadCompettion();
        r.value = 0 ;
        Thread t1 = new Thread(r, "Thread 1");
        Thread t2 = new Thread(r, "Thread 2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    @Override
    public void run() {
        compareAndSwap(0, 1) ;
    }
}
