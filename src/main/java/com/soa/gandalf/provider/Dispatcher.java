package com.soa.gandalf.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 适配器
 *
 * @author think <syj247@qq.com>、
 * @date 2020/8/10、13:51
 */
@Component
public class Dispatcher {

    @Autowired
    private InProviderAnalyzer inProviderAnalyzer;
    @Autowired
    private OutProviderAnalyzer outProviderAnalyzer;


    public void inbound(int pact, String from, String to, String cmd, Map<String, Object> param) {
        Inable inable = inProviderAnalyzer.analyzeInable(pact);
        GandalfInMessage gandalfInMessage = inable.format(from, to, cmd, param, pact);
        inable.addInQueue(gandalfInMessage);
    }

    public Outable outbound(int pact) {
        return outProviderAnalyzer.analyzeOutable(pact);
    }


    public GandalfOutMessage process(GandalfInMessage gandalfInMessage) {
        Outable outable = outProviderAnalyzer.analyzeOutable(gandalfInMessage.getPact());
        return outable.doSomething(gandalfInMessage);

    }

}
