package com.yicj.study.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName: HelloLogger
 * Description: TODO(描述)
 * Date: 2020/7/16 10:49
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class HelloLogger {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(HelloLogger.class) ;
        logger.info("hello world");

        Logger logger2 = LoggerFactory.getLogger(HelloLogger.class) ;
        logger2.info("hello world 2");
    }
}
