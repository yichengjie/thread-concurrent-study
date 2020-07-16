package com.yicj.study.producerconsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

/**
 * 支持流量控制的通道实现
 * ClassName: SemaphoreBasedChannel
 * Description: TODO(描述)
 * Date: 2020/7/16 17:37
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class SemaphoreBasedChannel<P> implements Channel<P> {
    private final BlockingQueue<P> queue ;
    private final Semaphore semaphore ;


    /**
     * @param queue  阻塞队列，通常是一个无界队列
     * @param flowLimit 流量限制数
     */
    public SemaphoreBasedChannel(BlockingQueue<P> queue, Semaphore flowLimit) {
        this.queue = queue;
        this.semaphore = flowLimit;
    }


    @Override
    public P take() throws InterruptedException {
        return queue.take();
    }

    @Override
    public void put(P product) throws InterruptedException {
        semaphore.acquire();
        try {
            queue.put(product);
        }finally {
            semaphore.release();
        }
    }
}
