package com.yicj.study.activeobject.stage2.make;

import com.yicj.study.activeobject.stage2.ActiveObject;
import com.yicj.study.activeobject.stage2.Result;
import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: MakerClientThread
 * Description: TODO(描述)
 * Date: 2020/7/17 10:07
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Slf4j
public class MakerClientThread extends Thread {

    private final ActiveObject activeObject ;
    private final char fillChar ;

    public MakerClientThread(String name, ActiveObject activeObject){
        super(name);
        this.activeObject = activeObject ;
        this.fillChar = name.charAt(0) ;
    }

    @Override
    public void run() {
        try {
            for (int i =0; true ; i++){
                // 有返回值的调用
                Result<String> result = activeObject.makeString(i, fillChar);
                Thread.sleep(10);
                String value = result.getResultValue();
                log.info("{} : value = {}", Thread.currentThread().getName(), value);
            }
        }catch (InterruptedException e){

        }
    }
}
