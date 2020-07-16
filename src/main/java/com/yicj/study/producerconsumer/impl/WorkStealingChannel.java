package com.yicj.study.producerconsumer.impl;

import com.yicj.study.producerconsumer.WorkStealingEnabledChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingDeque;

/**
 * ClassName: WorkStealingChannel
 * Description: TODO(描述)
 * Date: 2020/7/16 20:02
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Slf4j
public class WorkStealingChannel<T> implements WorkStealingEnabledChannel<T> {

    private final BlockingDeque<T> [] managedQueues ;

    public WorkStealingChannel(BlockingDeque<T> [] managedQueues){
        this.managedQueues = managedQueues ;
    }

    @Override
    public T take(BlockingDeque<T> preferredQueue) throws InterruptedException {
        // 优先从指定的受管队列中取“产品”
        BlockingDeque<T> targetQueue = preferredQueue ;
        T product = null ;
        // 试图从指定的队列首取“产品”
        if (targetQueue != null){
            product = targetQueue.poll() ;
        }
        int queueIndex = -1 ;
        while (product == null){
            queueIndex = (queueIndex + 1) % managedQueues.length ;
            targetQueue = managedQueues[queueIndex] ;
            // 试图从其受管的队列尾“窃取”查产品
            product = targetQueue.pollLast();
            if (preferredQueue == targetQueue){
                break;
            }
        }
        if (product == null){
            // 随机“窃取”其他受管队列的“产品”
            queueIndex = (int) (System.currentTimeMillis() % managedQueues.length) ;
            targetQueue = managedQueues[queueIndex] ;
            product = targetQueue.takeLast() ;
            log.info("stolen from {} : {}", queueIndex, product);
        }
        return product;
    }

    @Override
    public T take() throws InterruptedException {
        return take(null);
    }

    @Override
    public void put(T product) throws InterruptedException {
        int targetIndex = (product.hashCode() % managedQueues.length) ;
        BlockingDeque<T> targetQueue = managedQueues[targetIndex] ;
        targetQueue.put(product);
    }
}
