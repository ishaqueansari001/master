package org.chatting.app;

import java.util.Scanner;

import org.chatting.app.redisImpl.RedisMessageListener;
import org.chatting.app.redisImpl.RedisPublisherImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootApplication
public class MyChattingApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(MyChattingApplication.class, args);
		Scanner scanner = new Scanner(System.in);
		while(true){
			RedisPublisherImpl redisPublisherImpl= new RedisPublisherImpl();
			redisPublisherImpl.publish(scanner.nextLine());
			scanner.nextLine();
			
		}
	}

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		return new JedisConnectionFactory();
	}

	@Bean
	RedisTemplate<String, Object> redisTemplate() {
		final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(jedisConnectionFactory());
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new GenericToStringSerializer<Object>(Object.class));
		template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
		return template;
	}

	MessageListenerAdapter messageListener() {
		return new MessageListenerAdapter(new RedisMessageListener());
	}

	@Bean
	RedisMessageListenerContainer redisContainer() {
		final RedisMessageListenerContainer container = new RedisMessageListenerContainer();

		container.setConnectionFactory(jedisConnectionFactory());
		container.addMessageListener(messageListener(), topic());

		return container;
	}

	@Bean
	ChannelTopic topic() {
		return new ChannelTopic("pubsub:queue");
	}
}
