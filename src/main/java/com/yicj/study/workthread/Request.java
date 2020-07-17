package com.yicj.study.workthread;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * ClassName: Request
 * Description: TODO(描述)
 * Date: 2020/7/17 21:27
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Slf4j
public class Request {

    private final String name ; // 委托者
    private final int number; // 请求的编号
    private static final Random random = new Random() ;

    public Request(String name, int number){
        this.name = name ;
        this.number = number ;
    }

    public void execute(){
        log.info("{} executes {}", Thread.currentThread().getName(), this);
        try {
            Thread.sleep(random.nextInt(1000));
        }catch (InterruptedException e){
        }
    }

    @Override
    public String toString(){
        return "[ Request from " + name +" No." + number + " ]";
    }
}
