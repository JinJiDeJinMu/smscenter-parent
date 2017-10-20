package com.sanerzone.smscenter;



import javax.annotation.PostConstruct;

import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.util.PatternMatcher;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.sanerzone.jeebase.common.utils.SpringContextHolder;

@Service
@Component
public class App {

	@PostConstruct
	public void test(){
		System.out.println("=====================");
//		try {
//			SendResult result = SpringContextHolder.getBean(DefaultMQProducer.class).send(new Message("BenchmarkTest", "111111".getBytes()));
//			System.out.println("==========="+result.getMsgId());
//		} catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public static void main(String[] args) {
		String pattern = "/**/*login";
		String path="/admin/ajaxlogin";
		PatternMatcher pathMatcher = new AntPathMatcher();
		System.out.println(pathMatcher.matches(pattern, path));
	}
}
