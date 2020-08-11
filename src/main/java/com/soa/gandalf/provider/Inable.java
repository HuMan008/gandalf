package com.soa.gandalf.provider;

import java.util.Map;

/**
 * @author think <syj247@qq.com>、
 * @date 2020/8/10、10:15
 */
public interface Inable {

    /**
     * 往入队列里丢
     *
     * @param gandalfInMessage
     * @return
     */
    void addInQueue(GandalfInMessage gandalfInMessage);

    /**
     * 格式转换
     *
     * @param from
     * @param to
     * @param cmd
     * @param param
     * @param pact
     * @return
     */
    GandalfInMessage format(String from, String to, String cmd, Map<String, Object> param, int pact);

    /**
     * 入列路由
     *
     * @param key
     * @return
     */
    String inRouterKey(String key);


}
