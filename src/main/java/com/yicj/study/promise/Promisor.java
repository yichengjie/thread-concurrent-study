package com.yicj.study.promise;

/**
 * 负责对外暴露可以返回Promise对象的异步方法，并启动异步任务的执行
 * ClassName: Promisor
 * Description: TODO(描述)
 * Date: 2020/7/16 15:29
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public interface Promisor {

    /**
     * 启动异步任务的执行，并返回用于获取异步任务执行结果的凭据对象
     * @return
     */
    Promise compute() ;
}
