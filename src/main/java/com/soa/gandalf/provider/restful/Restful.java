package com.soa.gandalf.provider.restful;

import com.alibaba.fastjson.JSON;
import com.soa.gandalf.config.consts.RabbitMQConsts;
import com.soa.gandalf.provider.GandalfInMessage;
import com.soa.gandalf.provider.GandalfOutMessage;
import com.soa.gandalf.provider.Inable;
import com.soa.gandalf.provider.Outable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Restful适配提供者
 *
 * @author think <syj247@qq.com>、
 * @date 2020/8/10、15:39
 */
@Component
@Slf4j
public class Restful implements Inable, Outable {

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    RabbitAdmin rabbitAdmin;
    @Autowired
    ConnectionFactory connectionFactory;

    /**
     * 往入队列里丢
     *
     * @param gandalfInMessage
     * @return
     */
    @Override
    public void addInQueue(GandalfInMessage gandalfInMessage) {
        //        rabbitAdmin.declareBinding(BindingBuilder.bind(RabbitMQConfigure.queueMap.get(RabbitMQConsts
        // .IN_QUEUE)).to((DirectExchange) RabbitMQConfigure.exchangeMap.get(RabbitMQConsts.IN_EXCHANGE)).with
        // (gandalfInMessage.getFrom()));
        //        //这里用JSON做序列号，
        //        rabbitTemplate.convertAndSend(RabbitMQConsts.IN_EXCHANGE,gandalfInMessage.getFrom() +
        // gandalfInMessage.getPact(),
        //                JSON.toJSONString(gandalfInMessage));

        rabbitTemplate.convertAndSend(RabbitMQConsts.IN_EXCHANGE, RabbitMQConsts.IN_ROUTER_KEY,
                JSON.toJSONString(gandalfInMessage));
    }


    /**
     * 格式转换
     *
     * @param from
     * @param to
     * @param cmd
     * @param param
     * @return
     */
    @Override
    public GandalfInMessage format(String from, String to, String cmd, Map<String, Object> param, int pact) {
        return new GandalfInMessage(from, to, cmd, param, pact);
    }


    /**
     * 调其他服务器的方法
     *
     * @param message
     * @return
     */
    @Override
    public GandalfOutMessage doSomething(GandalfInMessage message) {
        log.debug("from:\t{} \nto:\t{}\ncmd:\t{}\nparam:\t{}", message.getFrom(), message.getTo(), message.getCmd(),
                message.getParam());

        log.debug("1将参数转换成服务商所需参数---");
        log.debug("2执行服务的方法");
        log.debug("3获取返回值 并和转换成统一对象");
        GandalfOutMessage gandalfOutMessage = new GandalfOutMessage();
        gandalfOutMessage.setGandalfInMessage(message);
        gandalfOutMessage.setResult("INVOKE Over!");
        return gandalfOutMessage;

    }


    /**
     * 把消息转换成服务调用者的格式
     *
     * @param gandalfOutMessage
     * @return
     */
    @Override
    public Object format(GandalfOutMessage gandalfOutMessage) {
        return JSON.toJSONString(gandalfOutMessage);
    }

    /**
     * 入列路由
     *
     * @param key
     * @return
     */
    @Override
    public String inRouterKey(String key) {
        return key;
    }

    /**
     * 出列路由
     *
     * @param key
     * @return
     */
    @Override
    public String outRouter(String key) {
        return key;
    }
}
