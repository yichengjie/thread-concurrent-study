package com.yicj.study.workthread;

/**
 * ClassName: Channel
 * Description: TODO(描述)
 * Date: 2020/7/17 21:33
 *
 * @author yicj(626659321 @ qq.com)
 * 修改记录
 * @version 产品版本信息 yyyy-mm-dd 姓名(邮箱) 修改信息
 */
public class Channel {

    private static final int MAX_REQUEST = 100 ;
    private final Request[] requestQueue ;
    private int tail ; // 下次putRequest的位置
    private int head ; // 下次takeRequest的位置
    private int count ;// Request的数量

    private final WorkerThread [] threadPool ;

    public Channel(int threads){
        this.requestQueue = new Request[MAX_REQUEST] ;
        this.head = 0 ;
        this.tail = 0 ;
        this.count = 0 ;
        threadPool = new WorkerThread[threads] ;
        for (int i =0 ; i < threadPool.length; i++){
            threadPool[i] = new WorkerThread("Worker-" + i, this) ;
        }
    }

    public void startWorkers(){
        for (int i =0; i < threadPool.length; i++){
            threadPool[i].start();
        }
    }

    public synchronized void putRequest(Request request){
        while (count >= requestQueue.length){
            try {
                wait();
            }catch (InterruptedException e){
            }
        }
        requestQueue[tail] = request ;
        tail = (tail +1) % requestQueue.length ;
        count ++ ;
        notifyAll();
    }

    public synchronized Request takeRequest(){
        while (count <= 0){
            try {
                wait();
            }catch (InterruptedException e){

            }
        }
        Request request = requestQueue[head] ;
        head = (head +1) % requestQueue.length ;
        count -- ;
        notifyAll();
        return request ;
    }

}
