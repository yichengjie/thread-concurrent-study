package com.yicj.study.producerconsumer;

import java.util.concurrent.BlockingQueue;

/**
 * 基于阻塞队列的通道实现
 * ClassName: BlockingQueueChannel
 * Description: TODO(描述)
 * Date: 2020/7/16 17:13
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class BlockingQueueChannel<P> implements Channel<P> {


    private final BlockingQueue<P> queue ;

    public BlockingQueueChannel(BlockingQueue<P> queue){
        this.queue = queue ;
    }


    @Override
    public P take() throws InterruptedException {
        return queue.take();
    }

    @Override
    public void put(P product) throws InterruptedException {
        queue.put(product);
    }
}
