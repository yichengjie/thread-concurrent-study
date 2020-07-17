package com.yicj.study.promise.custom;

/**
 * ClassName: FutureData
 * Description: TODO(描述)
 * Date: 2020/7/17 22:14
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class FutureData implements Data {

    private RealData realData = null ;
    private boolean ready = false ;

    public synchronized void setRealData(RealData realData){
        if (ready){
            return; // balk
        }
        this.realData = realData ;
        this.ready = true ;
        notifyAll();
    }

    @Override
    public String getContent() {
        while (!ready){
            try {
                wait();
            }catch (InterruptedException e){

            }
        }
        return realData.getContent();
    }
}
