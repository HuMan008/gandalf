package com.soa.gandalf.web.controller;


import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.soa.gandalf.config.consts.RabbitMQConsts;
import com.soa.gandalf.provider.Dispatcher;
import com.soa.gandalf.provider.ProviderEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Restful 入口
 *
 * @author think <syj247@qq.com>、
 * @date 2020/8/10、11:17
 */
@RestController
@Slf4j
public class RestfulController {


    @Autowired
    ConnectionFactory connectionFactory;

    @Autowired
    Dispatcher dispatcher;
    @Autowired
    RabbitAdmin rabbitAdmin;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/t1")
    @ResponseBody
    public Object method(@RequestParam String from, @RequestParam String to, @RequestParam String cmd) {
        //    /api/queues/vhost/name
        //参数校验 todo
        dispatcher.inbound(ProviderEnum.Restful.getCode(), from, to, cmd, new HashMap<>());
        Map<String, Object> map = new HashMap<>();
        map.put("auto_delete", true);
        map.put("count", 1);
        map.put("ackmode", "ack_requeue_true");
        map.put("encoding", "auto");
        map.put("truncate", "50000");

        // 路径要改成动态的 todo
        String url =
                "http://" + connectionFactory.getHost() + ":15672/api/queues/myhost/" + RabbitMQConsts.OUT_QUEUE +
                        "/get";
        return HttpRequest.post(url).basicAuth("test", "test").body(JSON.toJSONString(map)).execute().body();


    }


}
