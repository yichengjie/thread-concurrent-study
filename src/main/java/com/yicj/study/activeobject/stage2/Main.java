package com.yicj.study.activeobject.stage2;

/**
 * ClassName: Main
 * Description: TODO(描述)
 * Date: 2020/7/17 10:52
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class Main {

    public static void main(String[] args) {
        ActiveObject activeObject = ActiveObjectFactory.createActiveObject() ;
        new MakerClientThread("Alice", activeObject).start();
        new MakerClientThread("Bobby", activeObject).start();
        new DisplayClientThread("Chris", activeObject).start();
    }
}
