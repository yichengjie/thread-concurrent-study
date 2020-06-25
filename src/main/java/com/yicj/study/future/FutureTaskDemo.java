package com.yicj.study.future;

import javax.security.auth.callback.Callback;
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

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Task task = new Task() ;
        FutureTask<String> futureTask = new FutureTask<>(task) ;
        futureTask.run();

        String s = futureTask.get();
        System.out.println(s);
    }

    static class Task implements Callable<String>{
        @Override
        public String call() throws Exception {
            Thread.sleep(3000);
            String name = Thread.currentThread().getName();
            return name +" test";
        }
    }
}
