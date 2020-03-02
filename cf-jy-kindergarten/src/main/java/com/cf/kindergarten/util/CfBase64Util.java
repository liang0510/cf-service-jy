package com.cf.kindergarten.util;

import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @Project: cf
 * @Package: com.cf.xwxx.util
 * @Description: null
 * @Author: 梁涛
 * @CreateDate: 2018-5-28 上午 9:37
 * @ModificationHistory: （who whatTime doWhat）
 */
public class CfBase64Util {

    /**
     * 将文件转成base64 字符串
     *
     * @return *
     * @throws Exception
     */

    public static String encodeBase64File(MultipartFile file) throws Exception {

        InputStream inputStream = file.getInputStream();
        /*FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();*/
        byte[] buffer = new byte[(int) file.getSize()];

        inputStream.read(buffer);
        inputStream.close();
        return new BASE64Encoder().encode(buffer).replaceAll("\r|\n", "");

    }

    /**
     * 将base64字符解码保存文件
     *
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */

    public static void decoderBase64File(String base64Code, String targetPath)
            throws Exception {
        byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code.replaceAll("\r|\n", ""));
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }


    /**
     * 加密操作
     *
     * @return
     */
    public static String encodeString(String str) {
//        String encodedText = "";
//        try {
//            BASE64Encoder encoder = new BASE64Encoder();
//            encodedText = encoder.encode(info.getBytes("GBK")).replaceAll("\r|\n", "");
////            encodedText = encoder.encode(info.getBytes());
//         //   Base64.Encoder encoder = Base64.getEncoder();
//
//        /*    byte[] textByte = info.getBytes("UTF-8");
//            encodedText = encoder.encodeToString(textByte);*/
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return encodedText;
        byte[] b=str.getBytes();
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs;
    }

    /**
     * 解密操作
     * @return
     */
    public static String decoderString(String str) {

//        BASE64Decoder decoder = new BASE64Decoder();
//
//        // Base64.Decoder decoder = Base64.getDecoder();
//        String deconde64 = null;
//        try {
//            deconde64 = new String(decoder.decodeBuffer(decodeStr.replaceAll("\r|\n", "")), "GBK");
//
//            //   deconde64 = new String(decoder.decode(decodeStr.replaceAll("\r|\n", "")), "UTF-8");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return deconde64;

        if (str == null)
            return null;
        str = str.trim();
        int len = str.length();
        if (len == 0 || len % 2 == 1)
            return null;

        byte[] b = new byte[len / 2];
        try {
            for (int i = 0; i < str.length(); i += 2) {
                b[i / 2] = (byte) Integer.decode("0x" + str.substring(i, i + 2)).intValue();
            }
            return new String(b);
        } catch (Exception e) {
            return null;
        }
    }

}
