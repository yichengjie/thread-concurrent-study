package com.yicj.study.cache;

import com.yicj.study.cache.computable.Computable;
import com.yicj.study.cache.computable.ExpensiveFunction;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 描述：     利用Future，避免重复计算
 */
public class ImoocCache7<A, V> implements Computable<A, V> {

    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();

    private final Computable<A, V> c;

    public ImoocCache7(Computable<A, V> c) {
        this.c = c;
    }

//    @Override
//    public V compute(A arg) throws Exception {
//        Future<V> f = cache.get(arg);
//        if (f == null) {
//            Callable<V> callable = () -> c.compute(arg);
//            FutureTask<V> ft = new FutureTask<>(callable);
//            f = ft;
//            cache.put(arg, ft);
//            System.out.println("从FutureTask调用了计算函数");
//            ft.run();
//        }
//        return f.get();
//    }


    @Override
    public V compute(A arg) throws Exception {
        Future<V> f = cache.get(arg);
        if (f == null) {
            Callable<V> callable = () -> c.compute(arg);
            FutureTask<V> ft = new FutureTask<>(callable);
            f = cache.putIfAbsent(arg,ft) ;
            if (f == null){
                f = ft ;
                System.out.println("从FutureTask调用了计算函数");
                ft.run();
            }
        }
        return f.get();
    }


    public static void main(String[] args) throws Exception {
        ImoocCache7<String, Integer> expensiveComputer =
                new ImoocCache7<>(new ExpensiveFunction());

        new Thread(()->{
            try {
                Integer result = expensiveComputer.compute("666");
                System.out.println("第一次的计算结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();


        new Thread(() -> {
            try {
                Integer result = expensiveComputer.compute("667");
                System.out.println("第二次的计算结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                Integer result = expensiveComputer.compute("666");
                System.out.println("第三次的计算结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }
}