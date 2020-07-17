package com.yicj.study.activeobject.stage1;

import com.yicj.study.activeobject.stage1.model.MMSDeliverRequest;

/**
 * ClassName: RequestPersistence
 * Description: TODO(描述)
 * Date: 2020/7/16 21:03
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public interface RequestPersistence {

    void store(MMSDeliverRequest request) ;
}
