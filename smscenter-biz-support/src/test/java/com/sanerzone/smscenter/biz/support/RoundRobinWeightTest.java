package com.sanerzone.smscenter.biz.support;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Lists;
import com.sanerzone.smscenter.biz.entity.GatewayWeight;
import com.sanerzone.smscenter.biz.entity.RoundRobinWeight;
 
public class RoundRobinWeightTest {
 
 
    public static void main(String[] args) {
    	
    	List<GatewayWeight> server = Lists.newArrayList();
    	
    	server.add(new GatewayWeight("YD0", 10));
    	server.add(new GatewayWeight("YD1",15));
    	server.add(new GatewayWeight("YD2",10));
    	server.add(new GatewayWeight("YD3",5));
    	server.add(new GatewayWeight("YD4",25));
    	
    	server.add(new GatewayWeight("YD5",5));
    	server.add(new GatewayWeight("YD6",5));
    	server.add(new GatewayWeight("YD7",10));
    	server.add(new GatewayWeight("YD8",11));
    	server.add(new GatewayWeight("YD9",9));
    	
    	final RoundRobinWeight roundRobinWeight = new RoundRobinWeight(server);
    	
        try {
            ExecutorService pool = Executors.newFixedThreadPool(1);
 
            for (int i = 0; i < 100; i++) {
                Runnable run = new Runnable() {
                    public void run() {
                        System.out.print(roundRobinWeight + "\r\n");
                    }
                };
             
                pool.execute(run);
            }
            // 关闭启动线程
            pool.shutdown();
            // 等待子线程结束，再继续执行下面的代码
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            System.out.println("all thread complete");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        /*
         * int req = 99; while (req >= 0) {
         * System.out.print(RoundRobinWeightTest.next() + ","); req--; }
         */
 
    }
}
