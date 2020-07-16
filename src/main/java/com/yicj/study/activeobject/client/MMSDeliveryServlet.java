package com.yicj.study.activeobject.client;

import com.yicj.study.activeobject.AsyncRequestPersistence;
import com.yicj.study.activeobject.model.MMSDeliverRequest;
import com.yicj.study.activeobject.model.Recipient;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

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
        Recipient shortNumberRecipient = mMsDeliverReq.getRecipient() ;
        Recipient originalNumberRecipient = null ;
        try {
            // 接收方短号转长号
            originalNumberRecipient = convertShortNumber(shortNumberRecipient) ;
        }catch (SQLException e){
            // 接收方短号转长号时发生数据库异常，触发请求消息的缓存
            AsyncRequestPersistence.getInstance().store(mMsDeliverReq);
            // 省略其他代码
            resp.setStatus(202);
        }
    }

    private Recipient convertShortNumber(Recipient shortNumberRecipient) throws SQLException {
        Recipient recipient = new Recipient() ;
        // 其他代码省略
        return recipient ;
    }


    private MMSDeliverRequest parseRequest(ServletInputStream inputStream) {
        MMSDeliverRequest mmsDeliverRequest = new MMSDeliverRequest() ;
        // 省略其他代码
        return mmsDeliverRequest ;
    }
}
