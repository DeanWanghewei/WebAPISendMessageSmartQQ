package com.superdean.smartqq.controller;

import com.google.common.base.Strings;
import com.superdean.smartqq.service.QQLoginService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;

/**
 * @Author DeanWanghewei@gmail.com
 * Time: 2018/11/26
 */
@RestController
public class LoginController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    QQLoginService loginService;


    @RequestMapping("/login")
    public void login(HttpServletRequest req, HttpServletResponse resp,
                      @RequestParam(value = "relogin", required = false) String relogin) {


        boolean newAccount = false;
        if (!Strings.isNullOrEmpty(relogin)) {
            newAccount = Boolean.valueOf(relogin);

        }

        if (loginService.getClientService().getSmartQQClient() != null && !newAccount) {
            try {
                resp.getWriter().write("you have already login an account," +
                        "if you want change account,please add a parameter relogin=ture");
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LOG.info("判断二维码位置：{} ", ClassLoader.getSystemResource("").getPath() + QQLoginService.QR_FILE_PATH);

        File file = new File(
                ClassLoader.getSystemResource("").getPath() + QQLoginService.QR_FILE_PATH);

        if (file.exists()) {
            file.delete();
            LOG.info("已经删除旧的二维码");
        }

        new Thread(loginService).start();
        while (!file.exists()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        resp.addHeader("Cache-Control", "no-store");

        try (final PrintWriter writer = resp.getWriter()) {
            final byte[] data = IOUtils.toByteArray(new FileInputStream(ClassLoader.getSystemResource("").getPath() + QQLoginService.QR_FILE_PATH));
            StringBuilder sb = new StringBuilder();
            sb.append("<html><body><img src=\"data:image/png;base64,").
                    append(Base64.getEncoder().encodeToString(data)).append("\"/></body></html>");
            writer.write(sb.toString());
            writer.flush();

        } catch (IOException e) {
            LOG.error("qr file write to html fail, please check it.", e);
        }

    }

    public static void main(String[] args) {
        String path = ClassLoader.getSystemResource("").getPath();
        System.out.println(path);

    }


}
