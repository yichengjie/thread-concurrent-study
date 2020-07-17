package com.yicj.study.workthread;

/**
 * ClassName: WorkerThread
 * Description: TODO(描述)
 * Date: 2020/7/17 21:40
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class WorkerThread extends Thread {
    private final Channel channel ;

    public WorkerThread(String name, Channel channel){
        super(name);
        this.channel = channel ;
    }

    @Override
    public void run() {
        while (true){
            Request request = channel.takeRequest() ;
            request.execute();
        }
    }
}
