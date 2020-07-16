package com.yicj.study.activeobject;

import com.yicj.study.activeobject.model.MMSDeliverRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ClassName: AsyncRequestPersistence
 * Description: TODO(描述)
 * Date: 2020/7/16 21:43
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
// 模式角色：Proxy
@Slf4j
public class AsyncRequestPersistence implements RequestPersistence {

    private static final long ONE_MINUTE_IN_SECONDS = 60 ;
    private final AtomicLong taskTimeConsumedPerInterval = new AtomicLong(0) ;
    private final AtomicInteger requestSubmittedPerIterval = new AtomicInteger(0) ;
    // 模式角色：Servant
    private final DiskBasedRequestPersistence delegate = new DiskBasedRequestPersistence() ;
    // 模式角色Scheduler
    private final ThreadPoolExecutor scheduler ;

    // 用于保存AsyncRequestPersistence的唯一实例
    private static class InstanceHolder{
        final static RequestPersistence INSTANCE = new AsyncRequestPersistence() ;
    }

    // 获取类AsyncRequestPersistence的唯一实例
    public static RequestPersistence getInstance(){
        return InstanceHolder.INSTANCE ;
    }

    private AsyncRequestPersistence(){
        scheduler = new ThreadPoolExecutor(
                1, 3,
                60 * ONE_MINUTE_IN_SECONDS, TimeUnit.SECONDS,
                // 模式角色ActivationQueue
                new ArrayBlockingQueue<>(200),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r, "AsyncRequestPersistence") ;
                        return t;
                    }
                }
        ) ;
        scheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 启动队列监控定时任务
        Timer monitorTimer = new Timer(true) ;
        monitorTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                log.info("task count: {}, Queue size: {}, taskTimeConsumedPerInterval: {} ms"
                        , requestSubmittedPerIterval, scheduler.getQueue().size(), taskTimeConsumedPerInterval.get());
                taskTimeConsumedPerInterval.set(0);
                requestSubmittedPerIterval.set(0);
            }
        }, 0 , ONE_MINUTE_IN_SECONDS * 1000);
    }


    @Override
    public void store(MMSDeliverRequest request) {
        // 对store方法的调用封装成MethodRequest对象，并存入缓冲区
        //模式角色：MethodRequest
        Callable<Boolean> methodRequest = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                long start = System.currentTimeMillis() ;
                try {
                    delegate.store(request);
                }finally {
                    taskTimeConsumedPerInterval.addAndGet(System.currentTimeMillis() - start) ;
                }
                return Boolean.TRUE;
            }
        } ;
        scheduler.submit(methodRequest) ;
        requestSubmittedPerIterval.incrementAndGet() ;
    }
}
