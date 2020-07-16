package com.yicj.study.promise.client.fileupload;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 数据同步模块的入口类
 * ClassName: DataSyncTask
 * Description: TODO(描述)
 * Date: 2020/7/16 15:43
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class DataSyncTask implements Runnable {
    private final Map<String,String> taskParameters ;

    public DataSyncTask(Map<String,String> taskParameters){
        this.taskParameters = taskParameters ;
    }

    @Override
    public void run() {
        String ftpServer = taskParameters.get("server") ;
        String ftpUserName = taskParameters.get("userName");
        String ftpPassword = taskParameters.get("password");
        // 先开始初始化FTP客户端实例
        Future<FTPClientUtil> ftpClientUtilPromise =
                FTPClientUtil.newInstance(ftpServer, ftpUserName, ftpPassword);
        // 查询数据库生成本地文件
        generateFilesFromDB() ;
        FTPClientUtil ftpClientUtil = null ;
        try {
            //获取初始化完毕的ftp客户端实例
            ftpClientUtil = ftpClientUtilPromise.get() ;
        }catch (InterruptedException ignore){

        }catch (ExecutionException e){
            throw new RuntimeException(e) ;
        }
        // 上传文件
        uploadFiles(ftpClientUtil) ;
        // 省略其他代码
    }

    public void generateFilesFromDB(){
        // 省略其他代码
    }

    public void uploadFiles(FTPClientUtil ftpClientUtil){
       Set<File> files = retrieveGeneratedFiles() ;
       for (File file: files){
           try {
               ftpClientUtil.upload(file) ;
           }catch (Exception e){
               e.printStackTrace();
           }
       }
    }

    private Set<File> retrieveGeneratedFiles() {
        Set<File> files = new HashSet<>() ;
        // 省略其他代码
        return files ;
    }

}
