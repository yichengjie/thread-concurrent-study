package com.yicj.study.activeobject.stage2;


/**
 * ClassName: DisplayStringRequest
 * Description: TODO(描述)
 * Date: 2020/7/17 10:31
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class DisplayStringRequest extends MethodRequest<Object> {

    private final String string ;

    public DisplayStringRequest(Servant servant, String string){
        super(servant, null);
        this.string = string ;
    }

    @Override
    public void execute() {
        servant.displayString(string);
    }
}
