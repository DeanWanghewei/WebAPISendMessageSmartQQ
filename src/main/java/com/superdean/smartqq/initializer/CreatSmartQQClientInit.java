package com.superdean.smartqq.initializer;

import com.superdean.smartqq.service.QQClientService;
import com.superdean.smartqq.service.QQLoginService;
import com.thankjava.toolkit3d.core.fastjson.FastJson;
import com.thankjava.wqq.SmartQQClient;
import com.thankjava.wqq.SmartQQClientBuilder;
import com.thankjava.wqq.entity.enums.LoginResultStatus;
import com.thankjava.wqq.entity.sys.LoginResult;
import com.thankjava.wqq.extend.ActionListener;
import com.thankjava.wqq.extend.CallBackListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @Author DeanWanghewei@gmail.com
 * Time: 2018/11/23
 */

@Component
public class CreatSmartQQClientInit {
    private static final Logger LOG = LoggerFactory.getLogger(CreatSmartQQClientInit.class);

    @Autowired
    QQClientService clientService;

    private SmartQQClientBuilder builder;

    private CallBackListener getQrListener;

    public void getQr() {

        /**
         * step 1 > 利用指定使用SmartQQClientBuilder指南来构建SmartQQClient实例
         */
        builder = SmartQQClientBuilder.custom(
                // 注册一个通知事件的处理器，它将在SmartQQClient获得到相关信息时被调用执行
                new MessageListener()
        );


        /**
         * step 2 > 自定义可选参数(为方便查看可选方法，设置参数的函数均以set关键字命名开始)
         */
        builder
                .setAutoGetInfoAfterLogin() // 设置登录成功后立即拉取一些信息
                .setExceptionRetryMaxTimes(3) // 设置如果请求异常重试3次
                .setAutoRefreshQrcode() // 设置若发现登录二维码过期则自动重新拉取
                .setOffLineListener(new CallBackListener() { // 注册一个离线通知 掉线后将被调用执行
                    @Override
                    public void onListener(ActionListener actionListener) {
                        LOG.info("登录的QQ已掉线无法继续使用(系统已经尝试自动处理)");
                        SmartQQClient smartQQClient = (SmartQQClient) actionListener.getData();
                        smartQQClient.shutdown();
                    }
                })
        ;

        /**
         * step 3 > create SmartQQClient 实例 并进行登录
         */

        // A: 声明一个获取到登录二维码的回调函数，将返回二维码的byte数组数据
        getQrListener = new CallBackListener() {

            // login 接口在得到登录二维码时会调用CallBackListener
            // 并且二维码byte[] 数据会通过ListenerAction.data返回

            @Override
            public void onListener(ActionListener actionListener) {

                try {
                    // 将返回的byte[]数据io处理成一张png图片
                    // 位于项目log/qrcode.png


                    ImageIO.write((BufferedImage) actionListener.getData(), "png",
                            new File(QQLoginService.QR_FILE_PATH));

                    LOG.info("获取登录二维码完成,手机QQ扫描 {} 位置的二维码图片",
                            QQLoginService.QR_FILE_PATH);
                } catch (Exception e) {
                    LOG.error("将byte[]写为图片失败", e);
                }

            }
        };

    }

    public void buildClient() {
        // B: 声明一个登录结果的函数回调，在登录成功或者失败或异常时进行回调触发
        CallBackListener loginListener = new CallBackListener() {

            // ListenerAction.data 返回登录结果 com.thankjava.wqq.entity.sys.LoginResult
            @Override
            public void onListener(ActionListener actionListener) {

                LoginResult loginResult = (LoginResult) actionListener.getData();
                LOG.info("登录结果: " + loginResult.getLoginStatus());

                if (loginResult.getLoginStatus() == LoginResultStatus.success) {

                    SmartQQClient smartQQClient = loginResult.getClient();

                    // TODO: 后续就可以利用smartQQClient调用API
                    LOG.info("获取到的好友列表信息: " + FastJson.toJSONString(smartQQClient.getFriendsList(true)));

                    clientService.setSmartQQClient(smartQQClient);

                    // 业务处理
                    // TODO:

                }
            }
        };

        // C: 进行登录,启动服务
        builder.createAndLogin(getQrListener, loginListener);

    }
}
