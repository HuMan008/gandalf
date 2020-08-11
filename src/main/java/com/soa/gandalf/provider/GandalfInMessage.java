package com.soa.gandalf.provider;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 调用服务前的统一对象
 *
 * @author think <syj247@qq.com>、
 * @date 2020/8/10、15:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GandalfInMessage {
    /**
     * 从哪来
     */
    String from;
    /*
     * 到哪去
     */ String to;
    /**
     * 需要执行什么
     */
    String cmd;
    /**
     * 执行需要的参数
     */
    Map<String, Object> param;

    /**
     * 用什么适配器
     */
    int pact;
}
