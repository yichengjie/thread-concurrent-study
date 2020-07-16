package com.yicj.study.producerconsumer;

/**
 * 对通道参与者进行抽象
 * ClassName: Channel
 * Description: TODO(描述)
 * Date: 2020/7/16 17:11
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public interface Channel <P>{
    /**
     * 从通道中取出一个产品
     * @return
     * @throws InterruptedException
     */
    P take() throws InterruptedException;

    /**
     * 向通道中存储一个产品
     * @param product
     */
    void put(P product) throws InterruptedException;
}
