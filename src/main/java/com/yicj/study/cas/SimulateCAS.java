package com.yicj.study.cas;

import java.util.PriorityQueue;

/**
 * ClassName: SimulateCAS
 * Description: TODO(描述)
 * Date: 2020/6/26 21:01
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class SimulateCAS {

    private volatile int value ;

    public synchronized int compareAndSwap(int expecteValue, int newValue){
        int oldValue = value ;
        if (expecteValue == oldValue){
            value = expecteValue ;
        }
        return oldValue ;
    }

    public static void main(String[] args) {

        String a = "a1" ;
        String b = "a" ;
        String c = b + "1" ;
        System.out.println(a ==  c);
        System.out.println(a.equals(c));

        PriorityQueue<Integer> queue = new PriorityQueue<>() ;
    }
}
