package com.yicj.study.promise.client.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * ClassName: FutureTaskDemo
 * Description: TODO(描述)
 * Date: 2020/6/25 11:18
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class FutureTaskDemo {

    static Logger log = LoggerFactory.getLogger(FutureTaskDemo.class) ;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Task task = new Task() ;
        FutureTask<String> futureTask = new FutureTask<>(task) ;
        futureTask.run();

        String s = futureTask.get();
        log.info(s);
    }

    static class Task implements Callable<String>{
        @Override
        public String call() throws Exception {
            Thread.sleep(300);
            String name = Thread.currentThread().getName();
            return name +" test";
        }
    }
}
