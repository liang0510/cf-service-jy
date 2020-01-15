package com.cf.kindergarten.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import net.coobird.thumbnailator.Thumbnails;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;

public class ImgBase64Util {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String strImg = GetImageStr("d://","loading_more.gif");
        System.out.println(strImg);
        GenerateImage("R0lGODlhJQAWAPcAAAAAAP///6OeoKumqmxocqejr7SxuoiHkjU1Ny0tLkJCQ6ytvaSmt52frm1u\r\ndpSXp6WouFxeZoOImHd7iIiMmFteZX6BhygpKgBtxQBuxABvxABuwgBuwQBvwQBywwBwwQBvwABw\r\nwABwvwBxvwBxvgBxvQBxvAB6xgB6xAB2wQB2wAB2vwB2vgByvQByvABzvABxuwByuwByugBzugBy\r\nuQBzuQBrrQBrrABpqgBpqQBoqABopwBlpQBlpABclAFoqAFpqAFnpgFkogFlogFkoQFakwFYjgJz\r\nuQN0uAR0vgR0uAZztwd1uwd2ugh2uwt1uw92tABzuABopgBakAFloQJ3vQN0twZ0tgh3tAl4tgl3\r\ntQp1tQp2sw16uA12sQ55tRN3shV3rRB4rhJ5rRR4rhZ6rxZ5qxd5rhd5rBh7rBt7rRp5qhp5qRx7\r\nqiR7pxh8qxl6qRp6qByArBx6px19qh17px99qhl8pCF8oyN/piN9pCZ9oSmAoSl+nyt+nit/nh59\r\nni6AnDOBmTSBmjeBmSaAmi6BmjSBlzaDlzeDlzuDkz2Ekz2FkUOFkUCFkEOHj0OGjkaGi0eHikeH\r\niUqHiktMTEhJSYqLi02Jh0GCfVCJhECDelaLgVeMgVeLgEmGdUmGdlyMflyMfV2OfF6NemCOemGP\r\nemKOekt+ZmKPeFGDZ2KQd1F/Y1KDZVODZGaQdWmRdGqRdWySclyHYHGUb3GUbWqHVHyWZnOMT4SL\r\nP4KGQ4+SPzMzMnl5eKSko46LNpKPOY6LPZGNS5uRPZuRP6OUMZSLQ6CTRKqWK6iZVbyhW7+naLyi\r\nZbaeZ+DFluDGltvIp8y3lObTs9HApciyksy7ocGxns6+q8i9t7quqUJBQZWVlYmJiWtra2JiYlNT\r\nU0xMTEhISENDQ0FBQUBAQD09PTIyMi0tLf///wAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\r\nAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACH5BAEAAOgALAAAAAAlABYA\r\nQAj/AI0AEUJEiJAhCKlQEaJwCJGHQxo6jEhRoUEqQ4IUobJiRo0aHmeInOEigxNk2Chc8mVpQrZi\r\nSTAooSES5MgZLHYMWRHlo82QM0SskbVITRc5VdKQGhVGA8ifNXHq7OjzZsgWSBSVcuQmFK1JVz54\r\n/BgVZBQWOnZGKSvSBRwwmZRB0BYO3DleC5ZtenRIBNCRNVbs+HFiheHDiBMrXpwYxQ2ONaL09NjT\r\n50cZMFyYKBGj6lPLlVfo4DiW7MgSbGKdGYPrWDJhtt604YSHxNqfH6OIJk22qghIdFhVc6Aggbhx\r\n5iJIc8UHE4mbNnVPLR0yiokjqQhBAeZsAAEBz36V/1n0p4RVwKLVQjUt8kWLEWbmkAnBwcTakE/H\r\nzhBMOIUKxSmkgJiA/60g4IEGHoYgCjZAht9fIplgAhdeeFEHDBuYQB1gIp21A2/6ASYCGp408oQW\r\nhWDRhCSdMLLBefgJxht7NllBBiDEUFPAAQZEM8wdehjSAYRP7bYCVCGJgEginzDzQDncbNNNLw00\r\nA8ogmphXmlmC7YSkSDKskgcq01hAzjfeVDJOBdCoYocoQ0LnkYxU9TZDFCD4UQsTu1gjwQUIlMPA\r\nNbl4QEkgIeSHnk4pWMZhCHG80kcWswRjjC6tfCHILXuMcB9gIHVZJ5IgibCEGJ6cYoossESyhQj3\r\n0SsIUk5eUkZWT5VFJkMJI5QgQ2SeTWZTDSxIMQUOPfCg7LLMNuvss8zm4ENAADs=","C:\\JetBrains\\apache-tomcat-6.0.45\\webapps\\eap1.0\\head\\121213.gif");
    }
    //图片转化成base64字符串
    public static String GetImageStr(String basePath,String filePathName) {
        if(!"\\".equals(basePath.substring(basePath.length() - 1, basePath.length()))){
            basePath = basePath+File.separator;
        }
        File file = new File(basePath+filePathName);
        String newPath = "";
        // 如果图片不存在生成默认图片
        if(!file.exists()){
            newPath = basePath + "default.jpg";
        } else {
            try {
            	newPath = file.toString();
            } catch (Exception ex) {
                ex.printStackTrace();
                return "error";
            }
        }
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String imgFile = newPath;//待处理的图片  
        file = new File(imgFile);
        if(!file.exists()){
            return "error";
        }

        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组  
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
            in = null;
        }
        catch (IOException e){
            e.printStackTrace();
        } finally{
            if(in != null){
                try {
                    in.close();
                    in = null;
                    file.delete();//删除图片
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //对字节数组Base64编码  
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//返回Base64编码过的字节数组字符串  
    }

    //base64字符串转化成图片  
    public static String GenerateImage(String imgStr,String imgFilePath)  {
        //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空  
            return "error";
        BASE64Decoder decoder = new BASE64Decoder();
        try{
            //Base64解码  
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i){
                if(b[i]<0)
                {//调整异常数据  
                    b[i]+=256;
                }
            }
            //生成jpeg图片  
            //String imgFilePath = "d://222.jpeg";//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return "success";
        }
        catch (Exception e){
            return "error";
        }
    }
    
    /**
     * 压缩并保存图片
     * @param oldSavePath 原文件路径
     * @param oldFileName 原文件名称
     * @param fix 文件类型
     * @return
     * @throws IOException
     */
    public static String thumbnailatorImage(String oldSavePath,String oldFileName,String fix) throws IOException {
        //Thumbnail读取并压缩图片
        BufferedImage waterMarkBufferedImage = Thumbnails.of(oldSavePath + oldFileName)
                //Thumbnail的方法,压缩图片
                .scale(0.5f)
                        //读取成BufferedImage对象
                .asBufferedImage();
        //把内存中的图片写入到指定的文件中
        String savePath = oldSavePath+"/phone/";
        File saveFile = new File(savePath);
        if (!saveFile.isDirectory())
            saveFile.mkdirs();


        UUID uuid = UUID.randomUUID();
        String fileName = uuid.toString();
        fileName += "." + fix;
        String saveFileName = savePath+fileName;
        File fileOutPut = new File(saveFileName);
        ImageIO.write(waterMarkBufferedImage, fix, fileOutPut);
        return saveFileName;
    }

    /**
     * 根据产品列表，查询对应的产品图片
     * @param list
     * @param uploadPath 路径
     * @return
     */
    public static Boolean imgUrl(List list,String uploadPath){
        try {
            for(Object obj : list){
                HashMap hashMap = (HashMap)obj;
                String fileName = hashMap.get("iproduct") + "_" + hashMap.get("ibrand") + "_" + hashMap.get("ipackage") + "_911.jpg";
                String strImg = GetImageStr(uploadPath,fileName);
                hashMap.put("strImg",strImg);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }
    
}
