package com.yicj.study.producerconsumer.client.workstealing;

import com.yicj.study.producerconsumer.impl.WorkStealingChannel;
import com.yicj.study.producerconsumer.WorkStealingEnabledChannel;
import com.yicj.study.termination.AbstractTerminableThread;
import com.yicj.study.termination.TerminationToken;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * ClassName: WorkStealingExample
 * Description: TODO(描述)
 * Date: 2020/7/16 20:18
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Slf4j
public class WorkStealingExample {

    private final WorkStealingEnabledChannel<String> channel ;
    private final TerminationToken token = new TerminationToken() ;

    public WorkStealingExample(){
        int nCpu = Runtime.getRuntime().availableProcessors() ;
        int consumerCount = nCpu /2 +1 ;
        BlockingDeque<String> [] managedQueues = new LinkedBlockingDeque[consumerCount] ;
        // 该通道实例对应了多个队列实例managedQueues
        channel = new WorkStealingChannel<>(managedQueues) ;

        Consumer [] consumers = new Consumer[consumerCount] ;
        for (int i = 0 ; i < consumerCount; i ++){
            managedQueues[i] = new LinkedBlockingDeque<>() ;
            consumers[i] = new Consumer(token, managedQueues[i]) ;
        }

        for (int i =0; i < nCpu; i++){
            new Producer().start();
        }

        for (int i =0; i < consumerCount; i++){
            consumers[i].start();
        }
    }


    public void doSomething(){

    }

    public static void main(String[] args) throws InterruptedException {
        WorkStealingExample wse = new WorkStealingExample() ;
        wse.doSomething();
        Thread.sleep(3500);
    }


    private class Producer extends AbstractTerminableThread{
        private int i = 0 ;
        @Override
        protected void doRun() throws Exception {
            channel.put(String.valueOf(i++));
        }
    }

    private class Consumer extends AbstractTerminableThread{
        private final BlockingDeque<String> workQueue ;

        public Consumer(TerminationToken token, BlockingDeque<String> workQueue){
            super(token);
            this.workQueue = workQueue ;
        }

        @Override
        protected void doRun() throws Exception {
            String product = channel.take(workQueue) ;
            log.info("Processing product: {}", product);
            // 模拟执行真正操作的时间消耗
            try {
                Thread.sleep(new Random().nextInt(50));
            }catch (InterruptedException e){
            }finally {
                token.reservations.decrementAndGet() ;
            }

        }
    }
}
