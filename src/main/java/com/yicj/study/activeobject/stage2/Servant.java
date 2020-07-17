package com.yicj.study.activeobject.stage2;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: Servant
 * Description: TODO(描述)
 * Date: 2020/7/17 10:04
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Slf4j
public class Servant implements ActiveObject{

    @Override
    public Result<String> makeString(int count, char fillChar) {
        char [] buffer = new char[count] ;
        for (int i = 0 ; i < count ; i++){
            buffer[i] = fillChar ;
            try {
                Thread.sleep(100);
            }catch (InterruptedException e){

            }
        }
        return new RealResult<>(new String(buffer));
    }

    @Override
    public void displayString(String string) {
        try {
            log.info("displayString :{}", string);
            Thread.sleep(10);
        }catch (InterruptedException e){

        }
    }
}
