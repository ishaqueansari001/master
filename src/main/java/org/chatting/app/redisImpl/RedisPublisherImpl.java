package org.chatting.app.redisImpl;


import org.chatting.app.redisImpl.IRedisPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
public class RedisPublisherImpl implements IRedisPublisher {
	@Autowired
	private RedisTemplate<String, Object> template;
	@Autowired
	private ChannelTopic topic;

	public void publish(String message) {
		
		template.convertAndSend(topic.getTopic(), message);
	}
}