package com.superdean.smartqq.initializer;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

/**
 * @Author DeanWanghewei@gmail.com
 * Time: 2018/11/23
 */
public class PrintLocalImage {
    public static String getQr(String text, int pxSize) {
        String s = "生成二维码失败";
        int width = 10;
        int height = 10;

        if (pxSize != 0) {
            width = pxSize;
            height = pxSize;
        }

        // 用于设置QR二维码参数
        Hashtable<EncodeHintType, Object> qrParam = new Hashtable<EncodeHintType, Object>();
        // 设置QR二维码的纠错级别——这里选择最低L级别
        qrParam.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        qrParam.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, qrParam);
            s = toAscii(bitMatrix);
        } catch (WriterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return s;
    }

    public static String getQr(String url) {
        return getQr(url, 10);
    }


    public static String getUrlFromQrFile(String filePath) {

        String returnMeaage = "get file qr fail";
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(filePath));
            BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
            Result result = new MultiFormatReader().decode(binaryBitmap);
            returnMeaage = result.getText();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return returnMeaage;
    }

    public static String toAscii(BitMatrix bitMatrix) {
        StringBuilder sb = new StringBuilder();
        for (int rows = 0; rows < bitMatrix.getHeight(); rows++) {
            for (int cols = 0; cols < bitMatrix.getWidth(); cols++) {
                boolean x = bitMatrix.get(rows, cols);
                if (!x) {
                    // white
                    sb.append("\033[47m  \033[0m");
                } else {
                    sb.append("\033[40m  \033[0m");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        System.out.println(getQr(getUrlFromQrFile("qrcode.png")));
    }

}
