package com.yicj.study.termination.client.producerconsumer;

import com.yicj.study.termination.AbstractTerminableThread;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * ClassName: SomeService
 * Description: TODO(描述)
 * Date: 2020/7/16 14:58
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Slf4j
public class SomeService {

    private final BlockingQueue<String> queue = new ArrayBlockingQueue<>(100) ;
    private final Producer producer = new Producer() ;
    private final Consumer consumer = new Consumer() ;

    private class Producer extends AbstractTerminableThread {
        private int i = 0 ;

        @Override
        protected void doRun() throws Exception {
            queue.put(String.valueOf(i++));
            consumer.terminationToken.reservations.incrementAndGet() ;
        }
    }

    private class Consumer extends AbstractTerminableThread{
        @Override
        protected void doRun() throws Exception {
            String product = queue.take();
            log.info("Processing product: {}", product);
            // 模拟执行真正操作的时间消耗
            try {
                Thread.sleep(new Random().nextInt(100));
            }catch (InterruptedException e){
                // do nothing
            }finally {
                terminationToken.reservations.decrementAndGet() ;
            }
        }
    }


    public void shutdown(){
        // 生产者线程停止后再停止消费者线程
        producer.terminate();
        consumer.terminate();
    }

    public void init(){
        producer.start();
        consumer.start();
    }

    public static void main(String[] args) throws InterruptedException {
        SomeService ss = new SomeService() ;
        ss.init();
        Thread.sleep(500);
        ss.shutdown();
    }
}
