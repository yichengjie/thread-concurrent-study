package com.yicj.study.activeobject;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * ClassName: CustomScheduler
 * Description: TODO(描述)
 * Date: 2020/7/16 22:42
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class CustomScheduler implements Runnable {

    private LinkedBlockingDeque<Runnable> activationQueue = new LinkedBlockingDeque<>() ;

    @Override
    public void run() {
        dispatch() ;
    }

    public <T> Future<T> enqueue(Callable<T> methodRequest){
        final FutureTask<T> task = new FutureTask<T>(methodRequest){
            @Override
            public void run() {
                try {
                    super.run();
                    // 捕获所有可能抛出的对象，避免该任务失败而导致所在线程终止
                }catch (Throwable e){
                    this.setException(e);
                }
            }
        } ;
        try {
            activationQueue.put(task);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
        return task ;
    }

    private void dispatch() {
        while (true){
            Runnable methodRequest ;
            try {
                methodRequest = activationQueue.take() ;
                //防止个别任务执行失败导致线程终止代码在run方法中
                methodRequest.run() ;
            }catch (InterruptedException e){
                // 处理该异常
            }
        }
    }

}
