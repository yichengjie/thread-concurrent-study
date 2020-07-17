package com.yicj.study.activeobject.stage2;

/**
 * ClassName: RealResult
 * Description: TODO(描述)
 * Date: 2020/7/17 10:02
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class RealResult<T> extends Result<T> {
    private final  T resultValue ;

    public RealResult(T resultValue){
        this.resultValue = resultValue;
    }

    @Override
    public T getResultValue() {
        return resultValue;
    }
}
