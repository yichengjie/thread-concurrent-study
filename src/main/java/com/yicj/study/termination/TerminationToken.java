package com.yicj.study.termination;

import java.lang.ref.WeakReference;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程停止状态
 * ClassName: TerminationToken
 * Description: TODO(描述)
 * Date: 2020/7/16 12:52
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class TerminationToken {
    // 使用volatile修饰，以保证无须显示锁的情况下该变量的内存可见性
    protected volatile boolean toShutdown = false ;
    public final AtomicInteger reservations = new AtomicInteger(0) ;
    //在多个可停止线程实例共享一个TerminationToken实例的情况下，
    //该队列用于记录那些共享TerminationToken实例的可停止线程，以便尽可能减少锁的使用情况下，
    // 实现这些线程的可停止。
    private final Queue<WeakReference<Terminatable>> coordinatedThreads ;

    public TerminationToken(){
        coordinatedThreads = new ConcurrentLinkedQueue<>() ;
    }

    public boolean isToShutdown(){
        return toShutdown ;
    }

    protected void setToShutdown(boolean toShutdown){
        this.toShutdown = toShutdown ;
    }

    protected void register(Terminatable thread){
        coordinatedThreads.add(new WeakReference<>(thread)) ;
    }


    //通知TerminationToken实例：共享该实例的所有可停止线程中的一个线程停止了，
    //以便其停止其他未被停止的线程
    protected  void notifyThreadTermination(Terminatable thread){
        WeakReference<Terminatable> wrThread ;
        Terminatable otherThread ;
        while ((wrThread = coordinatedThreads.poll()) != null){
            otherThread = wrThread.get() ;
            if (otherThread !=null && otherThread != thread){
                otherThread.terminate() ;
            }
        }
    }

}
