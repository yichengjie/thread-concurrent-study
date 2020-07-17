package com.yicj.study.promise.custom;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: RealData
 * Description: TODO(描述)
 * Date: 2020/7/17 22:14
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Slf4j
public class RealData implements Data {

    private final String content ;

    public RealData(int count, char c){
        log.info("making RealData({}, {}) BEGIN", count, c);
        char [] buffer = new char[count] ;
        for (int i =0; i < count; i++){
            buffer[i] = c;
            try {
                Thread.sleep(100);
            }catch (InterruptedException e){

            }
        }
        log.info("making ReadData({}, {}) END", count, c);
        this.content = new String(buffer) ;
    }

    @Override
    public String getContent() {
        return content;
    }
}
