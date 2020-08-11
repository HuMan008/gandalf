package com.soa.gandalf.provider;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.soa.gandalf.config.consts.RabbitMQConsts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;

/**
 * @author think <syj247@qq.com>、
 * @date 2020/8/10、17:08
 * 监听OutQueue 当有数据放进来，把数据转换成调用者所需的格式并返回
 * // 异步方法调动 或者无需返回的指令
 */

@Slf4j
//@Service
//@RabbitListener(queues = RabbitMQConsts.OUT_QUEUE)
public class OutQueueListener {

    @Autowired
    Dispatcher dispatcher;
    @Autowired
    RabbitTemplate rabbitTemplate;


    //    @RabbitHandler
    public void receive(String msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag, Message message) {
        try {
            log.debug("OutQueue recevie tag{} Msg:\t{}", tag, msg);
            GandalfOutMessage gandalfOutMessage = JSON.parseObject(msg, GandalfOutMessage.class);
            dispatcher.outbound(gandalfOutMessage.getGandalfInMessage().getPact()).format(gandalfOutMessage);
            //重新入列
            rabbitTemplate.convertAndSend(RabbitMQConsts.OUT_EXCHANGE, RabbitMQConsts.OUT_ROUTER_KEY, msg);
            //            channel.basicNack(tag,false,true);
        } catch (Exception e) {
            log.error("{}", e);
        }
    }
}
