package com.superdean.smartqq.service;

import com.thankjava.wqq.consts.MsgType;
import com.thankjava.wqq.entity.msg.SendMsg;
import com.thankjava.wqq.entity.wqq.FriendInfo;
import com.thankjava.wqq.entity.wqq.FriendsList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author DeanWanghewei@gmail.com
 * Time: 2018/11/26
 */
@Service
public class SendMessageService {
    private static final Logger LOG = LoggerFactory.getLogger(SendMessageService.class);
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

        SendMsg mes = new SendMsg(uni, MsgType.message, data);
        qqClientService.getSmartQQClient().sendMsg(mes);
        LOG.info("send a mesage {} ,to {}", friendsInfo.get(uni).getNickName(), mes);

        return true;


    }
}
