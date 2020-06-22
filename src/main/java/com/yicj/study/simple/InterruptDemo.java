package com.yicj.study.simple;

/**
 * ClassName: InterruptDemo
 * Description: TODO(描述)
 * Date: 2020/6/22 20:33
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class InterruptDemo {

    public static void main(String[] args) throws InterruptedException {
        MyThread myThread = new MyThread();
        myThread.start();
        Thread.sleep(2000);
        myThread.interrupt();
        System.out.println("end");
    }

    static class MyThread extends Thread{
        @Override
        public void run() {
            try {
                for (int i = 0 ; i < 500000 ; i++){
                    if (this.isInterrupted()){
                        System.out.println("是停止状态....");
                        throw new InterruptedException() ;
                    }
                    System.out.println("i = " + (i+1));
                }
            }catch (InterruptedException e){
                System.out.println("进入异常方法了线程终止");
                e.printStackTrace();
            }

        }
    }
}
