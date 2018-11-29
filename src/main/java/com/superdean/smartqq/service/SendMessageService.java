package com.superdean.smartqq.service;

import com.thankjava.wqq.consts.MsgType;
import com.thankjava.wqq.entity.msg.SendMsg;
import com.thankjava.wqq.entity.wqq.FriendInfo;
import com.thankjava.wqq.entity.wqq.FriendsList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author DeanWanghewei@gmail.com
 * Time: 2018/11/26
 */
@Service
public class SendMessageService {
    @Autowired
    QQClientService qqClientService;

    public boolean sendMessage(String id, String data) {

        if (qqClientService.getSmartQQClient() == null) {
            return false;
        }

        Long uni = Long.valueOf(id);

        FriendsList friendsList = qqClientService.getSmartQQClient().getFriendsList(false);
        Map<Long, FriendInfo> friendsInfo = friendsList.getFriendsInfo();
        if (!friendsInfo.containsKey(uni)) {
            return false;
        }

        SendMsg test = new SendMsg(uni, MsgType.message, data);
        qqClientService.getSmartQQClient().sendMsg(test);
        return true;


    }
}
