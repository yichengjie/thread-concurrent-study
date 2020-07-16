package com.yicj.study.termination;

/**
 * ClassName: Terminable
 * Description: TODO(描述)
 * Date: 2020/7/16 12:51
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public interface Terminable {
    /**
     * 请求目标线程停止
     */
    void terminate() ;
}
