package com.soa.gandalf.provider;

/**
 * @author think <syj247@qq.com>、
 * @date 2020/8/10、15:39
 */
public interface Outable {

    /**
     * 调其他服务器的方法
     *
     * @param message
     * @return
     */
    GandalfOutMessage doSomething(GandalfInMessage message);


    /**
     * 把消息转换成服务调用者的格式
     *
     * @param gandalfOutMessage
     * @return
     */
    Object format(GandalfOutMessage gandalfOutMessage);

    /**
     * 出列路由
     *
     * @param key
     * @return
     */
    String outRouter(String key);
}
