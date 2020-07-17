package com.yicj.study.activeobject.stage2;

/**
 * ClassName: MakeStringRequest
 * Description: TODO(描述)
 * Date: 2020/7/17 10:22
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class MakeStringRequest extends MethodRequest<String> {

    private final int count ;
    private final char fillChar ;

    public MakeStringRequest(Servant servant, FutureResult<String> future, int count, char fillChar){
        super(servant, future);
        this.count = count ;
        this.fillChar = fillChar;
    }

    @Override
    public void execute() {
        Result<String> result = servant.makeString(count, fillChar);
        future.setResult(result);
    }
}
