package com.yicj.study.workthread;

import java.util.Random;

/**
 * ClassName: ClientThread
 * Description: TODO(描述)
 * Date: 2020/7/17 21:58
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class ClientThread extends Thread {

    private final Channel channel ;
    private static final Random random = new Random() ;
    public ClientThread(String name, Channel channel){
        super(name);
        this.channel = channel ;
    }

    @Override
    public void run() {
        try {
            for (int i =0 ; true; i++){
                Request request = new Request(getName(), i) ;
                channel.putRequest(request);
                Thread.sleep(random.nextInt(1000));
            }
        }catch (InterruptedException e){

        }
    }
}
