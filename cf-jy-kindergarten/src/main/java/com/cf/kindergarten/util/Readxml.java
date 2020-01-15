package com.cf.kindergarten.util;
import com.cf.core.util.ToolUtil;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import java.util.*;


/**
 * DMS工具包
 */
public class Readxml {

    public String readXML(String url,HashMap param) throws Exception {
        //单据封装的上传信息
       /* String url = this.getClass().getResource("").getFile();
        //获取程序发布路径
        url = url.replaceAll("%20", " ");
        url = url.substring(0, url.indexOf("phone") + 6) + param.get("templaterPath") + ".xml";*/
        SAXReader reader = new SAXReader();
        StringBuilder xmlStr = new StringBuilder();
        xmlStr.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        try {
            Document document = reader.read(url);
            Element element = document.getRootElement();
            List attributes = element.attributes(); //获取跟节点的属性值
            String property = "";
            for (int i = 0; i < attributes.size(); i++) {
                Attribute attribute = (Attribute) attributes.get(i);
                Object tVal = param.get(attribute.getQualifiedName());
                if (null == tVal) {
                    tVal = attribute.getValue();
                }
                property += " " + attribute.getQualifiedName() + "=" + "\"" + tVal + "\"";

            }
            xmlStr.append("<" + element.getQualifiedName() + property + ">");
            getSonElement(element, xmlStr, param, 0);
            xmlStr.append("</" + element.getQualifiedName() + ">");
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        System.out.print("");
        return xmlStr.toString();
    }
    public void getSonElement(Element ele, StringBuilder xmlStr, HashMap param, int index) {
        HashMap mainObj = (HashMap)param.get("mainObj");
        //获取当前节点的子节点
        List sons = ele.elements();
        if (0 != sons.size()) {
            for (Iterator i = ele.elementIterator(); i.hasNext(); ) {
                Element son = (Element) i.next();
                Element parent = son.getParent(); //当前节点的父节点
                String value = "";
                //判读父节点是否表头
                if (parent.getQualifiedName().equals("header")) {
                    value = mainObj.get(son.getQualifiedName()) + "";
                }else if (parent.getQualifiedName().equals("body")) {
                    List<HashMap> childList = (List<HashMap>)param.get("childList");
                    if(null == childList || childList.isEmpty()){
                        continue;
                    }
                    for(HashMap childObj:childList){
                        xmlStr.append("<entry>");
                        for(Iterator it = son.elementIterator(); it.hasNext(); ) {
                            Element gson = (Element) it.next();
                            xmlStr.append("<" + gson.getQualifiedName() + ">");
                            String tValue = childObj.get(gson.getQualifiedName())+"";
                            if (ToolUtil.isEmpty(tValue)) tValue = "";
                            xmlStr.append(tValue);
                            xmlStr.append("</" + gson.getQualifiedName() + ">");
                        }
                        xmlStr.append(" </entry>");
                    }
                    continue;
                }
                xmlStr.append("<" + son.getQualifiedName() + ">");
                if (value.equalsIgnoreCase("null")) value = "";
                xmlStr.append(value);
                getSonElement(son, xmlStr, param, index);
                xmlStr.append("</" + son.getQualifiedName() + ">");
            }
        }
    }

}