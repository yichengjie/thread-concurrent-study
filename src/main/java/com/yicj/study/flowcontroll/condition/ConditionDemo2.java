package com.yicj.study.flowcontroll.condition;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ClassName: ConditionDemo2
 * Description: TODO(描述)
 * Date: 2020/6/27 8:54
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class ConditionDemo2 {

    private int queueSize = 10 ;
    private PriorityQueue<Integer> queue = new PriorityQueue<>() ;

    private Lock lock = new ReentrantLock() ;
    // 生产者条件
    private Condition isFull = lock.newCondition() ;
    // 消费者条件
    private Condition isEmpty = lock.newCondition() ;


    public static void main(String[] args) {

        ConditionDemo2 conditionDemo2 = new ConditionDemo2() ;
        Producer producer = conditionDemo2.new Producer();
        Consumer consumer = conditionDemo2.new Consumer();

        consumer.start();
        producer.start();

    }

    class Consumer extends Thread{
        @Override
        public void run() {
            consume() ;
        }
        private void consume() {
            while (true){
                lock.lock();
                try {
                    //1. 如果队列已经为空则等待
                    while (queue.size() == 0){
                        System.out.println("队列空，等待数据");
                        isEmpty.await();
                    }
                    //2. 队列不为空则取出消费
                    queue.poll() ;
                    isFull.signalAll();
                    System.out.println("从队列中取走一个数据，队列剩余"+queue.size()+"个元素");
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        }
    }


    class Producer extends Thread{
        @Override
        public void run() {
            produce() ;
        }
        private void produce() {
            while (true){
                lock.lock();
                try {
                    //1. 如果队列已经满了则等待
                    while (queue.size() == queueSize){
                        System.out.println("队列满，等待空间。");
                        isFull.await();
                    }
                    //2. 队列不为空则取出消费
                    queue.offer(1) ;
                    isEmpty.signalAll();
                    System.out.println("向队列中放入一个数据，队列剩余"+queue.size()+"个元素");
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        }
    }

}
