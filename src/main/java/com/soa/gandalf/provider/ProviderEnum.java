package com.soa.gandalf.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author think <syj247@qq.com>、
 * @date 2020/8/10、16:21
 */
@Getter
@AllArgsConstructor
public enum ProviderEnum {
    Restful(1, "Restful"), RPC(2, "PRC"), IBM(3, "IBM"),

    ;
    int code;
    String descp;
}
