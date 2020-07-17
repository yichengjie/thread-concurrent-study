package com.yicj.study.promise.custom;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: Main
 * Description: TODO(描述)
 * Date: 2020/7/17 22:12
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Slf4j
public class Main {

    public static void main(String[] args) {
        log.info("main BEGIN");
        Host host = new Host() ;
        Data data1 = host.request(10, 'A' );
        Data data2 = host.request(10, 'B' );
        Data data3 = host.request(10, 'C' );
        log.info("main otherJob BEGIN");

        try {
            Thread.sleep(2000);
        }catch (Exception e){
        }

        log.info("main otherJob END");
        log.info("data1 = {}", data1.getContent());
        log.info("data2 = {}", data2.getContent());
        log.info("data3 = {}", data3.getContent());
    }
}
