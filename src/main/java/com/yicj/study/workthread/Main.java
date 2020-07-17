package com.yicj.study.workthread;

/**
 * ClassName: Main
 * Description: TODO(描述)
 * Date: 2020/7/17 21:26
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class Main {

    public static void main(String[] args) {
        Channel channel = new Channel(5) ;
        channel.startWorkers();

        new ClientThread("Alice", channel).start();
        new ClientThread("Bobby", channel).start();
        new ClientThread("Chris", channel).start();
    }
}
