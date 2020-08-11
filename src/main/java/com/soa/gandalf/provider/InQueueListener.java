package com.soa.gandalf.provider;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.soa.gandalf.config.consts.RabbitMQConsts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

/**
 * @author think <syj247@qq.com>、
 * @date 2020/8/10、17:08
 * 监听InQueue 当有数据放进来，就执行服务器的方法。并将结果转换成{@link GandalfOutMessage}
 */

@Slf4j
@Service
@RabbitListener(queues = RabbitMQConsts.IN_QUEUE)
public class InQueueListener {

    @Autowired
    Dispatcher dispatcher;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @RabbitHandler
    public void receive(String msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag, Message message) {
        try {
            log.debug("InQueue recevie Msg:\t{}", msg);
            GandalfInMessage gandalfInMessage = JSON.parseObject(msg, GandalfInMessage.class);
            //手动确认
            channel.basicAck(tag, true);
            GandalfOutMessage gandalfOutMessage = dispatcher.process(gandalfInMessage);

            rabbitTemplate.convertAndSend(RabbitMQConsts.OUT_EXCHANGE, RabbitMQConsts.OUT_ROUTER_KEY,
                    JSON.toJSONString(gandalfOutMessage));
            // 同步调用 带返回结果 。
            //获取后 转换格式  todo
            // rabbitTemplate.convertSendAndReceive()
        } catch (Exception e) {
            log.error("{}", e);
        }


    }
}
