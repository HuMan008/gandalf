package com.soa.gandalf.provider;

import com.soa.gandalf.provider.restful.Restful;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author think <syj247@qq.com>、
 * @date 2020/8/10、15:23
 */
@Component
public class OutProviderAnalyzer {

    @Autowired
    Restful restful;


    public Outable outFromProvider(ProviderEnum providerEnum) {
        switch (providerEnum) {
            case Restful:
                return restful;
            // 增加相应的适配器实现
            default:
                return null;
        }
    }


    public Outable analyzeOutable(int code) {
        if (ProviderEnum.Restful.getCode() == code) {
            return restful;
            // 增加相应的适配器实现
        } else {
            return null;
        }
    }
}
