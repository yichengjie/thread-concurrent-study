package com.yicj.study.activeobject.stage2;

/**
 * ClassName: DisplayClientThread
 * Description: TODO(描述)
 * Date: 2020/7/17 10:12
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class DisplayClientThread extends Thread {

    // 这里的activeObject是Proxy对象
    private final ActiveObject activeObject ;

    public DisplayClientThread(String name, ActiveObject activeObject){
        super(name);
        this.activeObject = activeObject ;
    }

    @Override
    public void run() {
        try {
            for (int i=0 ; true ;i++){
                //没有返回值的调用
                String string = Thread.currentThread().getName() +" " +i ;
                activeObject.displayString(string);
                Thread.sleep(200);
            }
        }catch (InterruptedException e){

        }
    }
}
