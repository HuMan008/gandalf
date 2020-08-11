package com.soa.gandalf.provider;

import com.soa.gandalf.provider.restful.Restful;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author think <syj247@qq.com>、
 * @date 2020/8/10、15:23
 */
@Component
public class InProviderAnalyzer {

    @Autowired
    Restful restful;


    public Inable inFromProvider(ProviderEnum providerEnum) {
        switch (providerEnum) {
            case Restful:
                return restful;
            // 增加相应的适配器实现
            default:
                return null;
        }
    }


    public Inable analyzeInable(int code) {
        if (ProviderEnum.Restful.getCode() == code) {
            return restful;
        } else {
            return null;
        }
    }
}
