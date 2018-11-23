package com.superdean.smartqq.controller;

import com.superdean.smartqq.service.QQClientService;
import com.thankjava.wqq.entity.wqq.FriendsList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author DeanWanghewei@gmail.com
 * Time: 2018/11/23
 */
@RestController
public class Hello {
    @Autowired
    QQClientService clientService;

    @RequestMapping("/helloWord")
    @ResponseBody
    public String sayHello() {
        FriendsList friendsList = clientService.getSmartQQClient().getFriendsList(false);
        System.out.println(friendsList.getFriendsInfo().keySet());

        return "success";
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String helloWord() {
        return "helloWord";
    }
}
