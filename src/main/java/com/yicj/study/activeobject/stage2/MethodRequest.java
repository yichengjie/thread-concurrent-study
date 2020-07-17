package com.yicj.study.activeobject.stage2;

/**
 * ClassName: MethodRequest
 * Description: TODO(描述)
 * Date: 2020/7/17 10:20
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public abstract class MethodRequest <T>{

    protected final Servant servant ;
    protected final FutureResult<T> future ;

    protected MethodRequest(Servant servant, FutureResult<T> future){
        this.servant = servant ;
        this.future = future ;
    }

    public abstract void execute() ;

}
