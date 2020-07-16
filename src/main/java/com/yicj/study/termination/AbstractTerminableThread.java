package com.yicj.study.termination;

/**
 * ClassName: AbstractTerminableThread
 * Description: TODO(描述)
 * Date: 2020/7/16 12:51
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public abstract class AbstractTerminableThread extends Thread implements Terminatable{

    // 模式角色
    public final TerminationToken terminationToken ;

    public AbstractTerminableThread(){
        this(new TerminationToken()) ;
    }

    public AbstractTerminableThread(TerminationToken terminationToken){
        this.terminationToken = terminationToken ;
        terminationToken.register(this);
    }

    /**
     * 留给子类实现其线程处理逻辑
     * @throws Exception
     */
    protected abstract void doRun() throws Exception;

    /**
     * 留给子类实现。用于实现线程停止后的一些清理动作
     * @param exp
     */
    protected void doCleanup(Exception exp){
        // 这里什么也不做
    }

    /**
     * 留给子类实现，用于执行线程停止所需的操作
     */
    protected void doTerminate(){
        // 这里什么也不做
    }



    @Override
    public void run() {
        Exception ex = null ;
        try {
            for (;;){
                // 在执行线程的处理逻辑前先判断线程停止的标志
                if (terminationToken.isToShutdown()
                        && terminationToken.reservations.get() <= 0){
                    break;
                }
                doRun();
            }
        }catch (Exception e){
            // 使得线程能够响应interrupt调用而退出
            ex = e ;
        }finally {
            try {
                doCleanup(ex);
            }finally {
                terminationToken.notifyThreadTermination(this);
            }
        }
    }

    @Override
    public void interrupt() {
        terminate();
    }

    @Override
    public void terminate() {
        terminationToken.setToShutdown(true);
        try {
            doTerminate() ;
        }finally {
            // 若无待处理的任务，则试图强制终止线程
            if (terminationToken.reservations.get() <= 0){
                super.interrupt();
            }
        }
    }


    public void terminate(boolean waitUtilThreadTerminated){
        terminate();
        if (waitUtilThreadTerminated){
            try {
                this.join();
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }
}
