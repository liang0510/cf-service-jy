package com.cf.kindergarten.util;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncodingAttributes;

import java.io.File;

/**
 * @Project : cf-dms
 * @Package Name : com.cf.dms.web
 * @Description : 平台保存前操作
 * @Author : Lenovo
 * @Creation Date : 2019-04-28 9:13
 * @ModificationHistory Who When What
 * @CfSaveBefore 保存前注解，funCode需要注入的功能码
 * SeniorResultModel beforeParams 原始参数 Map<String,Object> map 前端的原始参数 可以通过ID是否空，判断新建、修改
 * SeniorResultModel afterParams 系统操作后的返回值，操作前为NULL
 * SeniorResultModel customParams 用户自定义参数，支持参数传递，操作前->操作中->操作后
 * SeniorResultModel resultModel 返回状态，默认成功
 * return 参数继续传递，beforeParams必须与原始参数格式一致
 * _________ ________________
 */


public class WavToMp3 {
    public static boolean wavTomp3(String inPath, String outFile) {
        boolean status = false;
        File file = new File( inPath );
        try {
            execute( file, outFile );
            status = true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            status = false;
            e.printStackTrace();
        }
        return status;
    }
    /**
     * 执行转化
     *
     * @param source      输入文件
     * @param desFileName 目标文件名
     * @return 转换之后文件
     */
    public static File execute(File source, String desFileName)
            throws Exception {
        File target = new File( desFileName );
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec( "libmp3lame" );
        audio.setBitRate( new Integer( 36000 ) );
        audio.setChannels( new Integer( 2 ) );
        audio.setSamplingRate( new Integer( 44100 ) );
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat( "mp3" );
        attrs.setAudioAttributes( audio );
        Encoder encoder = new Encoder();
        encoder.encode( source, target, attrs );
        return target;
    }
    public static void delFile(String path,String filename){
        File file=new File(path+"/"+filename);
        if(file.exists()&&file.isFile())
            file.delete();
    }
    public static void main(String[] args) {
        String resourcePath = "D:\\eap\\fileupload\\webapps\\cf_file\\callcenter\\mUploadPath\\2019-04-25\\1.wav";
        String targerPath = "D:\\eap\\fileupload\\webapps\\cf_file\\callcenter\\mUploadPath\\2019-04-25\\1.mp3";
        boolean res= wavTomp3( resourcePath, targerPath );
        delFile("D:\\eap\\fileupload\\webapps\\cf_file\\callcenter\\mUploadPath\\2019-04-25","55190066-20190425135000-O-L01-EN-918012019949.wav");
        System.out.println(res);

    }

}
