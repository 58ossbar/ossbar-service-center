package com.ossbar.utils.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ossbar.utils.tool.StrUtils;

/**
 * rabbitmq消息队列生产者
 * @author zhuq
 *
 */
@Component
public class RabbitMqProducer {
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AmqpTemplate rabbitmqTemplate;

	/**
	 * 生产者发送消息
	 * @param queues 列队名称
	 * @param msg 消息内容
	 */
	public void send(String queues, String msg) {
		if(check(queues, msg)) {
			log.debug("send String msg["+msg+"] to queues["+queues+"]");
			this.rabbitmqTemplate.convertAndSend(queues, msg);
		}
	}
	
	/**
	 * 生产者发送消息
	 * @param queues 列队名称
	 * @param msg 消息内容
	 */
	public void send(String queues, Object msg) {
		if(check(queues, msg)) {
			log.debug("send String msg["+msg+"] to queues["+queues+"]");
			this.rabbitmqTemplate.convertAndSend(queues, msg);
		}
	}
	
	/**
	 * 生产者发送消息
	 * @param queues 列队名称
	 * @param msg 消息内容
	 */
	public void send(String queues, JSONObject msg) {
		if(check(queues, msg)) {
			log.debug("send JSONObject msg["+msg.toJSONString()+"] to queues["+queues+"]");
			this.rabbitmqTemplate.convertAndSend(queues, msg);
		}
	}
	/**
	 * 生产者发送消息
	 * @param queues 列队名称
	 * @param msg 消息内容
	 */
	public void send(String queues, JSONArray msg) {
		if(check(queues, msg)) {
			log.debug("send JSONArray msg["+msg.toJSONString()+"] to queues["+queues+"]");
			this.rabbitmqTemplate.convertAndSend(queues, msg);
		}
	}
	/**
	 * 发送消息前置校验
	 * @param queues 列队名称
	 * @param msg 消息内容
	 */
	private boolean check(String queues, Object msg) {
		if(StrUtils.isEmpty(queues) || StrUtils.isNull(msg)) {
			log.debug("queues or msg can not be null");
			return false;
		}
		return true;
	}
}
