package com.superdean.smartqq.service;

import com.superdean.smartqq.initializer.CreatSmartQQClientInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author DeanWanghewei@gmail.com
 * Time: 2018/11/26
 */
@Service
public class QQLoginService implements Runnable {
    public static final String QR_FILE_PATH = "qrconde.png";
    @Autowired
    QQClientService clientService;

    @Autowired
    CreatSmartQQClientInit creatSmartQQClientInit;

    public QQClientService getClientService() {
        return clientService;
    }

    @Override
    public void run() {
        creatSmartQQClientInit.getQr();
        creatSmartQQClientInit.buildClient();

    }
}
