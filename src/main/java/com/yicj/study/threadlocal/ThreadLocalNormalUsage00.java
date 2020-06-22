package com.yicj.study.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ClassName: ThreadLocalNormalUsage00
 * Description: TODO(描述)
 * Date: 2020/6/22 21:48
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class ThreadLocalNormalUsage00 {


    public static void main(String[] args) {
        new Thread(()->{
            String date = new ThreadLocalNormalUsage00().date(10);
            System.out.println(date);
        }).start(); ;

        new Thread(()->{
            String date = new ThreadLocalNormalUsage00().date(1007);
            System.out.println(date);
        }).start(); ;
    }

    public String date(int seconds){
        // 参数的单位是毫秒，从1970.1.1 00:00:00 GMT记时
        Date date = new Date(1000 * seconds) ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss") ;
        return dateFormat.format(date) ;
    }

}
