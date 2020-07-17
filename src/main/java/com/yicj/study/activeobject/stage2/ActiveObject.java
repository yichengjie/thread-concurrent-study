package com.yicj.study.activeobject.stage2;

/**
 * ClassName: ActiveObject
 * Description: TODO(描述)
 * Date: 2020/7/17 10:04
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public interface ActiveObject {

    Result<String> makeString(int count, char fillChar) ;

    void displayString(String string) ;
}
