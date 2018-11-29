package com.superdean.smartqq.service;

import com.alibaba.fastjson.JSON;
import com.thankjava.wqq.entity.wqq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author DeanWanghewei@gmail.com
 * Time: 2018/11/26
 */
@Service
public class ClientInfoService {

    @Autowired
    QQClientService clientService;

    public String getClientInfo() {
        if (clientService.getSmartQQClient() == null) {
            return "you have not login an account";
        }

        StringBuffer sb = new StringBuffer();
        FriendsList friendsList =
                clientService.getSmartQQClient().getFriendsList(false);

        Map<Long, FriendInfo> friendsInfo = friendsList.getFriendsInfo();

        HashMap<String, Map<Long, ?>> stringMapHashMap = new HashMap<>();


        sb.append("FriendList :");

        for (Map.Entry<Long, FriendInfo> friendInfoEntry : friendsInfo.entrySet()) {
            sb.append("uin: ").append(friendInfoEntry.getKey()).append("\r\n")
                    .append("nickName: ").append(friendInfoEntry.getValue().getNickName()).append("\r\n");
        }

        stringMapHashMap.put("FriendList", friendsInfo);


        sb.append("discusList : \r\n");
        DiscusList discusList = clientService.getSmartQQClient().getDiscusList(false);
        Map<Long, DiscuInfo> discus = discusList.getDiscus();
        for (Map.Entry<Long, DiscuInfo> entry : discus.entrySet()) {
            sb.append("uin: ").append(entry.getKey()).append("\r\n")
                    .append("discusName: ").append(entry.getValue().getName()).append("\r\n");
        }

        stringMapHashMap.put("discusList", discus);

        sb.append("froupList: \r\n");
        GroupsList groupsList = clientService.getSmartQQClient().getGroupsList(false);
        Map<Long, GroupInfo> groups = groupsList.getGroups();
        for (Map.Entry<Long, GroupInfo> entry : groups.entrySet()) {
            sb.append("uin: ").append(entry.getKey()).append("\r\n")
                    .append("discusName: ").append(entry.getValue().getName()).append("\r\n");
        }
        stringMapHashMap.put("froupList", groups);

        return JSON.toJSONString(stringMapHashMap);

//        return sb.toString();
    }
}
