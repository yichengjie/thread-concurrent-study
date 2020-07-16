package com.yicj.study.promise;

/**
 *
 * 包装异步任务处理结果的凭证对象
 * ClassName: Promise
 * Description: TODO(描述)
 * Date: 2020/7/16 15:30
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public interface Promise  {
    /**
     * 获取与其所属Promise实例关联的异步任务的执行结果
     * @return
     */
    Result getResult() ;

    /**
     * 设置与其所属Promise实例关联的异步任务的执行结果
     * @param result
     */
    void setResult(Result result) ;

    /**
     * 检测与其所属Promise实例关联的异步任务是否执行完毕
     * @return
     */
    boolean isDone() ;
}
