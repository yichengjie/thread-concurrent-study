package com.yicj.study.termination.client;

import com.yicj.study.termination.AbstractTerminableThread;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 告警发送线程类
 * ClassName: AlarmSendingThread
 * Description: TODO(描述)
 * Date: 2020/7/16 12:50
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
@Slf4j
public class AlarmSendingThread extends AbstractTerminableThread {

    private final AlarmAgent alarmAgent = new AlarmAgent() ;

    // 告警队列
    private final BlockingQueue<AlarmInfo> alarmQueue ;
    private final ConcurrentMap<String, AtomicInteger> submittedAlarmRegistry ;

    public AlarmSendingThread(){
        alarmQueue = new ArrayBlockingQueue<>(100) ;
        submittedAlarmRegistry = new ConcurrentHashMap<>() ;
        alarmAgent.init() ;
    }


    @Override
    protected void doRun() throws Exception {
        AlarmInfo alarm = alarmQueue.take() ;
        terminationToken.reservations.decrementAndGet() ;
        try {
            //将告警信息发送至告警服务器
            alarmAgent.sendAlarm(alarm) ;
        }catch (Exception e){
            e.printStackTrace();
        }
        // 处理恢复告警：将相应的故障告警从注册表中删除，使得相应故障恢复后若再次出现相同故障，
        // 该故障信息能够上报到服务器
        if (AlarmType.RESUME == alarm.type){
            String key = AlarmType.FAULT.toString() +":" +alarm.getId() +"@" + alarm.getExtraInfo() ;
            submittedAlarmRegistry.remove(key) ;
            key = AlarmType.RESUME.toString() +":" +alarm.getId() +"@" + alarm.getExtraInfo() ;
            submittedAlarmRegistry.remove(key) ;
        }
    }



    public int sendAlarm(final AlarmInfo alarmInfo){
        AlarmType type = alarmInfo.type ;
        String id = alarmInfo.getId() ;
        String extraInfo = alarmInfo.getExtraInfo();
        if (terminationToken.isToShutdown()){
            // 记录告警
            log.error("rejected alarm:" + id +"," +extraInfo);
            return -1 ;
        }
        int duplicateSubmissionCount = 0 ;
        try {
            String key = type.toString()+":" + id +"@" + extraInfo ;
            AtomicInteger  prevSubmittedCounter = submittedAlarmRegistry.putIfAbsent(key, new AtomicInteger(0)) ;
            if (null == prevSubmittedCounter){
                terminationToken.reservations.incrementAndGet() ;
                alarmQueue.put(alarmInfo);
            }else {
                //故障未恢复，不用重复发送告警信息给服务器，故障增加计数
                duplicateSubmissionCount = prevSubmittedCounter.incrementAndGet() ;
            }
        }catch (Exception t){
            t.printStackTrace();
        }
        return duplicateSubmissionCount ;
    }


    @Override
    protected void doCleanup(Exception exp) {
        if (exp !=null && !(exp instanceof InterruptedException)){
            exp.printStackTrace();
        }
        alarmAgent.disconnect() ;
    }
}
