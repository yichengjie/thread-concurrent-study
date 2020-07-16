package com.yicj.study.promise.client.fileupload;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * FTP客户端工具类源码
 * ClassName: FTPClientUtil
 * Description: TODO(描述)
 * Date: 2020/7/16 15:49
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Slf4j
public class FTPClientUtil {

    private final FTPClient ftp = new FTPClient() ;

    private final Map<String, Boolean> dirCreateMap = new HashMap<>() ;

    private FTPClientUtil(){}

    public static Future<FTPClientUtil> newInstance(String ftpServer, String username, String password) {
        Callable<FTPClientUtil> callback = () -> {
            FTPClientUtil self = new FTPClientUtil() ;
            self.init(ftpServer, username, password) ;
            return self;
        };
        // task 相当于模式角色Promise
        final FutureTask<FTPClientUtil> task = new FutureTask<>(callback) ;
        /**
         * 下面这行代码与本案例的实际代码并不一致，这是为了讨论方便
         * 下面新建的线程相当于模式角色TaskExecutor
         */
        new Thread(task).start();
        return task ;
    }

    private void init(String ftpServer, String username, String password) throws Exception{
        FTPClientConfig config = new FTPClientConfig() ;
        ftp.configure(config);
        int reply ;
        ftp.connect(ftpServer);
        log.info("reply info : {}", ftp.getReplyString());
        reply = ftp.getReplyCode() ;
        if (!FTPReply.isPositiveCompletion(reply)){
            ftp.disconnect();
            throw new RuntimeException("FTP server refused connection.") ;
        }
        boolean isOK = ftp.login(username, password) ;
        if (isOK){
            log.info("reply info : {}", ftp.getReplyString());
        }else {
            throw new RuntimeException("Failed to login. " +ftp.getReplyString()) ;
        }
        reply = ftp.cwd("~/subspsync") ;
        if (!FTPReply.isPositiveCompletion(reply)){
            ftp.disconnect();
            throw new RuntimeException("Failed to change working director. reply: " + reply) ;
        }else {
            log.info("reply info : {}", ftp.getReplyString());
        }
        ftp.setFileType(FTP.ASCII_FILE_TYPE) ;
    }

    public void upload(File file) throws Exception{
        InputStream dataIn = new BufferedInputStream(new FileInputStream(file), 1024 * 8) ;
        boolean isOk ;
        String dirName = file.getParentFile().getName() ;
        String fileName = dirName +"/" + file.getName() ;
        ByteArrayInputStream checkFileInputStream = new ByteArrayInputStream("".getBytes()) ;
        try {
            if (!dirCreateMap.containsKey(dirName)){
                ftp.makeDirectory(dirName) ;
                dirCreateMap.put(dirName, null) ;
            }
            try {
                isOk = ftp.storeFile(fileName, dataIn) ;
            }catch (IOException e){
                throw new RuntimeException("Failed to upload " + fileName, e) ;
            }
            if (isOk){
                ftp.storeFile(fileName +".c", checkFileInputStream) ;
            }else {
                throw new RuntimeException("Failed to upload " + fileName +", reply : " + ftp.getReplyString()) ;
            }
        }finally {
            dataIn.close();
        }
    }


    public void disconnect(){
        if (ftp.isConnected()){
            try {
                ftp.disconnect();
            }catch (IOException ignore){

            }
        }
    }

}
