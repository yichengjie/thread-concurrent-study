package com.yicj.study.activeobject.stage2;

/**
 * ClassName: ActiveObjectFactory
 * Description: TODO(描述)
 * Date: 2020/7/17 10:16
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class ActiveObjectFactory {

    public static ActiveObject createActiveObject(){
        Servant servant = new Servant() ;
        ActivationQueue queue = new ActivationQueue() ;
        SchedulerThread scheduler = new SchedulerThread(queue) ;
        Proxy proxy = new Proxy(scheduler, servant) ;
        scheduler.start();
        return proxy ;
    }
}
