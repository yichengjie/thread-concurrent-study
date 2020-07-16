package com.yicj.study.activeobject;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 彩信下发请求处理的入口
 * ClassName: MMSDeliveryServlet
 * Description: TODO(描述)
 * Date: 2020/7/16 20:59
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class MMSDeliveryServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 将请求中的数据解析为内部对象
        MMSDeliverRequest mMsDeliverReq = this.parseRequest(req.getInputStream()) ;


    }


    private MMSDeliverRequest parseRequest(ServletInputStream inputStream) {
        MMSDeliverRequest mmsDeliverRequest = new MMSDeliverRequest() ;
        // 省略其他代码
        return mmsDeliverRequest ;
    }
}
