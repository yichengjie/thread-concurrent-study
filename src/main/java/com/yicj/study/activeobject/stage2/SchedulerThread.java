package com.yicj.study.activeobject.stage2;

/**
 * ClassName: SchedulerThread
 * Description: TODO(描述)
 * Date: 2020/7/17 10:17
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class SchedulerThread extends Thread {
    private final ActivationQueue queue ;

    public SchedulerThread(ActivationQueue queue){
        this.queue = queue ;
    }

    public void invoke(MethodRequest request){
        queue.putRequest(request);
    }

    @Override
    public void run() {
        while (true){
            MethodRequest request = queue.takeRequest() ;
            request.execute();
        }
    }
}
