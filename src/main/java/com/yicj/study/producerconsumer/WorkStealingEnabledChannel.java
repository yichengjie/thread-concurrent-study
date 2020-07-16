package com.yicj.study.producerconsumer;

import java.util.concurrent.BlockingDeque;

/**
 * ClassName: WorkStealingEnabledChannel
 * Description: TODO(描述)
 * Date: 2020/7/16 19:59
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public interface WorkStealingEnabledChannel<P> extends Channel<P> {

    P take(BlockingDeque<P> preferredQueue) throws InterruptedException;
}
