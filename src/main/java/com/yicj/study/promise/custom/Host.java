package com.yicj.study.promise.custom;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: Host
 * Description: TODO(描述)
 * Date: 2020/7/17 22:22
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Slf4j
public class Host {

    public Data request(final int count, final char c){
        log.info("request({}, {}) BEGIN", count, c);
        // (1) 创建FutureData的实例
        final FutureData future = new FutureData();
        // (2) 启动一个新线程，用于创建RealData的实例
        new Thread(() -> {
            RealData realData = new RealData(count, c) ;
            future.setRealData(realData);
        }).start();
        log.info("request ({}, {}) END", count, c);
        // （3）返回FutureData的实例
        return future ;
    }
}
