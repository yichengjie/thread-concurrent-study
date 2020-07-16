package com.yicj.study.termination;

import lombok.Data;

/**
 * ClassName: AlarmInfo
 * Description: TODO(描述)
 * Date: 2020/7/16 13:06
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class AlarmInfo {
    private String id ;
    private String extraInfo ;
    public AlarmType type ;
    public AlarmInfo(String id, AlarmType type) {
        this.id = id ;
        this.type = type ;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }
}
