package com.soa.gandalf.config.consts;

/**
 * Rabbit consts Define
 *
 * @author think <syj247@qq.com>、
 * @date 2020/8/10、10:01
 */
public class RabbitMQConsts {


    public static final String IN_QUEUE = "com.soa.gandalf.in";
    public static final String IN_QUEUE_DEAD = "com.soa.gandalf.in.dead";


    public static final String OUT_QUEUE = "com.soa.gandalf.out";
    public static final String OUT_QUEUE_DEAD = "dlx.com.soa.gandalf.out.dead";

    public static final String IN_EXCHANGE = "com.soa.gandalf.in";
    public static final String IN_EXCHANGE_DEAD = "dlx.com.soa.gandalf.in.dead";
    public static final String OUT_EXCHANGE = "com.soa.gandalf.out";
    public static final String OUT_EXCHANGE_DEAD = "dlx.com.soa.gandalf.out.dead";


    public static final String IN_ROUTER_KEY = "com.soa.gandalf.in.#";
    public static final String OUT_ROUTER_KEY = "com.soa.gandalf.out.#";
}
