package org.chatting.app.redisImpl;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class RedisMessageListener implements MessageListener {
	public RedisMessageListener() {
		// TODO Auto-generated constructor stub
	}
	public void onMessage(Message message, byte[] pattern) {
	//send to all sbuscribers
		System.out.println(message.toString());
	}
}