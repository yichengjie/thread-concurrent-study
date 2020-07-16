package com.yicj.study.producerconsumer.client.attachement;

import com.yicj.study.producerconsumer.impl.BlockingQueueChannel;
import com.yicj.study.producerconsumer.Channel;
import com.yicj.study.termination.AbstractTerminableThread;

import java.io.*;
import java.text.Normalizer;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 对附件生成全文检索需要的索引文件
 * ClassName: AttachmentProcessor
 * Description: TODO(描述)
 * Date: 2020/7/16 17:15
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class AttachmentProcessor {

    private final String ATTACHMENT_STORE_BASE_DIR = "/home/viscent/tmp/attachments/" ;

    // 模式角色- Channel
    private final Channel<File> channel =
            new BlockingQueueChannel<>(new ArrayBlockingQueue<>(200)) ;

    public void saveAttachment(
            InputStream in, String documentId, String originalFileName)throws IOException {
        File file = saveAsFile(in, documentId, originalFileName) ;
        try {
            channel.put(file);
        }catch (InterruptedException ignore){

        }
        indexingThread.terminationToken.reservations.incrementAndGet() ;
    }

    // 模式角色Consumer
    private final AbstractTerminableThread indexingThread = new AbstractTerminableThread() {
        @Override
        protected void doRun() throws Exception {
            File file = channel.take() ;
            try {
                indexFile(file) ;
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                terminationToken.reservations.decrementAndGet() ;
            }
        }
    } ;

    public void init(){
        indexingThread.start();
    }

    public void shutdown(){
        indexingThread.terminate();
    }

    // 根据指定文件生成全文搜索需要的索引文件
    private void indexFile(File file) throws Exception{
        // 省略其他代码
        // 模拟生成索引文件的时间消耗
        Random random = new Random() ;
        try {
            Thread.sleep(random.nextInt(100));
        }catch (InterruptedException ignore){

        }
    }

    private File saveAsFile(
            InputStream in, String documentId, String originalFileName)throws IOException {
        String dirName = ATTACHMENT_STORE_BASE_DIR + documentId ;
        File dir = new File(dirName) ;
        dir.mkdirs() ;
        File file = new File(dirName, Normalizer.normalize(originalFileName,Normalizer.Form.NFC)) ;
        // 防止目录跨越攻击
        if (!dirName.equals(file.getCanonicalFile().getParent())){
            throw new SecurityException("Invalid originalFileName : " + originalFileName) ;
        }
        BufferedOutputStream bos = null ;
        BufferedInputStream bis = new BufferedInputStream(in) ;
        byte [] buf = new byte[2048] ;
        int len ;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file)) ;
            while ((len = bis.read(buf)) > 0){
                bos.write(buf, 0 , len);
            }
            bos.flush();
        }finally {
            try {
                bis.close();
            }catch (IOException e){
            }
            try {
                if (bos !=null){
                    bos.close();
                }
            }catch (IOException e){
            }
        }
        return file ;
    }
}
