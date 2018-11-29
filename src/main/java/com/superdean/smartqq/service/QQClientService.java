package com.superdean.smartqq.service;

import com.thankjava.wqq.SmartQQClient;
import org.springframework.stereotype.Component;

/**
 * @Author DeanWanghewei@gmail.com
 * Time: 2018/11/23
 */
@Component
public class QQClientService {

    SmartQQClient smartQQClient;

    public SmartQQClient getSmartQQClient() {
        return smartQQClient;
    }

    public void setSmartQQClient(SmartQQClient smartQQClient) {
        this.smartQQClient = smartQQClient;
    }
}
