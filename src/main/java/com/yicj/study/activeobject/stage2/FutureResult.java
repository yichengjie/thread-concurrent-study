package com.yicj.study.activeobject.stage2;

/**
 * ClassName: FutureResult
 * Description: TODO(描述)
 * Date: 2020/7/17 9:59
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class FutureResult<T> extends Result<T> {

    private Result<T> result ;
    private boolean ready = false ;

    public synchronized void setResult(Result<T> result){
        this.result = result ;
        this.ready = true ;
        notifyAll();
    }

    @Override
    public T getResultValue() {
        while (!ready){
            try {
                wait();
            }catch (InterruptedException e){

            }
        }
        return result.getResultValue();
    }
}
