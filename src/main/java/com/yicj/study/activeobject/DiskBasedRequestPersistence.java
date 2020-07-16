package com.yicj.study.activeobject;

import com.yicj.study.activeobject.model.MMSDeliverRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ClassName: DiskBasedRequestPersistence
 * Description: TODO(描述)
 * Date: 2020/7/16 21:09
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Slf4j
public class DiskBasedRequestPersistence implements RequestPersistence {

    // 负责缓存文件的存储管理
    private final SectionBasedDiskStorage storage = new SectionBasedDiskStorage() ;

    @Override
    public void store(MMSDeliverRequest request) {
        // 申请缓存文件的文件名
        String [] fileNameParts = storage.apply4FileName(request);
        File file = new File(fileNameParts[0]) ;
        try {
            ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(file)) ;
            try {
                objOut.writeObject(request);
            }finally {
                objOut.close();
            }
        }catch (IOException e){
            storage.decrementSectionFileCount(fileNameParts[1]);
            log.error("Failed to store request ", e);
        }
    }


    class SectionBasedDiskStorage{
        private Deque<String> sectionNames = new LinkedList<>() ;
        /**
         * key->value：存储子目录->子目录下缓存文件计数器
         */
        private Map<String, AtomicInteger> sectionFileCountMap = new HashMap<>() ;
        private int maxFilesPerSection = 2000 ;
        private int maxSectionCount = 100 ;
        private String storageBaseDir = System.getProperty("user.dir") +"/vpn" ;
        private final Object sectionLock = new Object() ;


        public String [] apply4FileName(MMSDeliverRequest request){
            String sectionName ;
            int iFileCount ;
            boolean need2RemoveSection = false ;
            String [] fileName = new String[2] ;
            // 这里有同步代码块
            synchronized (sectionLock){
                // 获取当前存储子目录名
                sectionName = this.getSectionName() ;
                AtomicInteger fileCount = sectionFileCountMap.get(sectionName) ;
                iFileCount = fileCount.get() ;
                // 当前存储子目录已满
                if (iFileCount >= maxFilesPerSection){
                    if (sectionNames.size() >= maxSectionCount){
                        need2RemoveSection = true ;
                    }
                    //创建新的存储子目录
                    sectionName = this.makeNewSectionDir() ;
                    fileCount = sectionFileCountMap.get(sectionName) ;
                }
                iFileCount = fileCount.addAndGet(1) ;
            }
            fileName[0] = storageBaseDir +"/" + sectionName +"/"
                    + new DecimalFormat("0000").format(iFileCount) + "-"
                    + request.getTimeStamp().getTime() / 1000 +"-"
                    + request.getExpiry() + ".rq" ;
            fileName[1] = sectionName ;

            if (need2RemoveSection){
                // 删除最老的存储子目录
                String oldestSectionName = sectionNames.removeFirst() ;
                this.removeSection(oldestSectionName) ;
            }
            return fileName ;
        }

        public void decrementSectionFileCount(String sectionName){
            AtomicInteger fileCount = sectionFileCountMap.get(sectionName) ;
            if (fileCount != null){
                fileCount.decrementAndGet() ;
            }
        }

        private boolean removeSection(String sectionName){
            boolean result = true ;
            File dir = new File(storageBaseDir +"/" + sectionName) ;
            for (File file: dir.listFiles()){
                result = result && file.delete() ;
            }
            result = result && dir.delete() ;
            return result ;
        }

        private String getSectionName(){
            String sectionName ;
            if (sectionNames.isEmpty()){
                sectionName = this.makeNewSectionDir() ;
            }else {
                sectionName = sectionNames.getLast() ;
            }
            return sectionName ;
        }

        private String makeNewSectionDir(){
            SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss") ;
            String sectionName = sdf.format(new Date()) ;
            File dir = new File(storageBaseDir +"/" + sectionName) ;
            if (dir.mkdir()){
                sectionNames.addLast(sectionName);
                sectionFileCountMap.put(sectionName, new AtomicInteger(0)) ;
            }else {
                throw new RuntimeException("Cannot create section dir " + sectionName) ;
            }
            return sectionName ;
        }

    }
}
