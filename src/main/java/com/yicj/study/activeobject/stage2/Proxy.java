package com.yicj.study.activeobject.stage2;

import com.yicj.study.activeobject.stage2.display.DisplayStringRequest;
import com.yicj.study.activeobject.stage2.make.MakeStringRequest;

/**
 * ClassName: Proxy
 * Description: TODO(描述)
 * Date: 2020/7/17 10:17
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class Proxy implements ActiveObject {

    private final SchedulerThread scheduler ;
    private final Servant servant ;

    public Proxy(SchedulerThread scheduler, Servant servant){
        this.scheduler = scheduler ;
        this.servant = servant ;
    }

    @Override
    public Result<String> makeString(int count, char fillChar) {
        FutureResult<String> future = new FutureResult<>() ;
        scheduler.invoke(new MakeStringRequest(servant, future, count, fillChar)) ;
        return future;
    }

    @Override
    public void displayString(String string) {
        scheduler.invoke(new DisplayStringRequest(servant, string)) ;
    }
}
