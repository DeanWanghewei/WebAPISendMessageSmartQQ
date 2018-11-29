package com.superdean.smartqq.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.superdean.smartqq.service.ClientInfoService;
import com.superdean.smartqq.service.QQClientService;
import com.superdean.smartqq.service.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


/**
 * @Author DeanWanghewei@gmail.com
 * Time: 2018/11/26
 */
@RestController
public class MessageController {
    @Autowired
    SendMessageService sendMessageService;

    @Autowired
    QQClientService qqClientService;


    @Autowired
    ClientInfoService clientInfoService;


    @ResponseBody
    @RequestMapping("/message/{id}")
    public String sendMessage(@PathVariable String id,
                              @RequestParam(value = "data", required = false) String data) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("result", "send message success");
        if (Strings.isNullOrEmpty(data)) {
            data = "send a emp message";
        }
        boolean send = sendMessageService.sendMessage(id, data);
        if (!send) {
            result.put("result", "send message fail. ");
            result.put("QQInfo", clientInfoService.getClientInfo());
        }


        return JSON.toJSONString(result);
    }
}
