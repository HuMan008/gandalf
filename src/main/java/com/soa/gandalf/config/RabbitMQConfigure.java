package com.soa.gandalf.config;

import com.soa.gandalf.config.consts.RabbitMQConsts;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * rabbitmq动态配置
 *
 * @author think <syj247@qq.com>、
 * @date 2019-9-19、9:01
 */
@Configuration
@ConditionalOnClass(RabbitAdmin.class)
public class RabbitMQConfigure {


    public static Map<String, Queue> queueMap = new ConcurrentHashMap<>(4);
    public static Map<String, Exchange> exchangeMap = new ConcurrentHashMap<>(4);
    private static Map<String, Object> args = new HashMap<>();
    @Autowired
    ConnectionFactory connectionFactory;

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }


    @PostConstruct
    public void init() {

        //IN_QUEUE_DEAD CONFIG
        args.put("x-dead-letter-exchange", RabbitMQConsts.IN_EXCHANGE_DEAD);
        // In_Queue Config
        DirectExchange inDirectExchange = new DirectExchange(RabbitMQConsts.IN_EXCHANGE, true, false);
        rabbitAdmin(connectionFactory).declareExchange(inDirectExchange);
        //        args.put("x-message-ttl", 0);
        Queue inQueue = new Queue(RabbitMQConsts.IN_QUEUE, true, false, false, args);
        rabbitAdmin(connectionFactory).declareQueue(inQueue);
        // 这里可以根据from动态设置RouterKey routerkey 就用from的值。
        rabbitAdmin(connectionFactory).declareBinding(BindingBuilder.bind(inQueue).to(inDirectExchange).with(RabbitMQConsts.IN_ROUTER_KEY));
        DirectExchange inDeadDirectExchange = new DirectExchange(RabbitMQConsts.IN_EXCHANGE_DEAD, true, false);
        rabbitAdmin(connectionFactory).declareExchange(inDeadDirectExchange);
        Queue inDeadQueue = new Queue(RabbitMQConsts.IN_QUEUE_DEAD);
        rabbitAdmin(connectionFactory).declareQueue(inDeadQueue);
        // 这里可以动态设置RouterKey
        rabbitAdmin(connectionFactory).declareBinding(BindingBuilder.bind(inDeadQueue).to(inDeadDirectExchange).with(RabbitMQConsts.IN_ROUTER_KEY));
        queueMap.put(RabbitMQConsts.IN_QUEUE, inQueue);
        queueMap.put(RabbitMQConsts.IN_QUEUE_DEAD, inDeadQueue);
        exchangeMap.put(RabbitMQConsts.IN_EXCHANGE, inDirectExchange);
        exchangeMap.put(RabbitMQConsts.IN_EXCHANGE_DEAD, inDeadDirectExchange);


        //OUT_QUEUE config
        args.put("x-dead-letter-exchange", RabbitMQConsts.OUT_EXCHANGE_DEAD);
        DirectExchange outDirectExchange = new DirectExchange(RabbitMQConsts.OUT_EXCHANGE, true, false);
        rabbitAdmin(connectionFactory).declareExchange(outDirectExchange);
        args.put("x-message-ttl", 5000);
        Queue outQueue = new Queue(RabbitMQConsts.OUT_QUEUE, true, false, false, args);

        rabbitAdmin(connectionFactory).declareQueue(outQueue);
        rabbitAdmin(connectionFactory).declareBinding(BindingBuilder.bind(outQueue).to(outDirectExchange).with(RabbitMQConsts.OUT_ROUTER_KEY));
        DirectExchange outDeadDirectExchange = new DirectExchange(RabbitMQConsts.OUT_EXCHANGE_DEAD, true, false);
        rabbitAdmin(connectionFactory).declareExchange(outDeadDirectExchange);
        Queue outDeadQueue = new Queue(RabbitMQConsts.OUT_QUEUE_DEAD);
        rabbitAdmin(connectionFactory).declareQueue(outDeadQueue);
        // 这里可以动态设置Router
        rabbitAdmin(connectionFactory).declareBinding(BindingBuilder.bind(outDeadQueue).to(outDeadDirectExchange).with(RabbitMQConsts.OUT_ROUTER_KEY));
        queueMap.put(RabbitMQConsts.OUT_QUEUE, outQueue);
        queueMap.put(RabbitMQConsts.OUT_QUEUE_DEAD, outDeadQueue);
        exchangeMap.put(RabbitMQConsts.OUT_EXCHANGE, outDirectExchange);
        exchangeMap.put(RabbitMQConsts.OUT_EXCHANGE_DEAD, outDeadDirectExchange);
    }


}
