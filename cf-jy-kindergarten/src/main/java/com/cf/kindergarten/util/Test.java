package com.cf.kindergarten.util;

import com.alibaba.fastjson.JSONObject;
import com.cf.kindergarten.mybatis.cf_app.PersonJson;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {
        String aaa="120004";
        System.out.println("11200040002".matches(aaa+"(.*)"));
        System.out.println("git");


        List list = new ArrayList<>();
        list.add("s1");
        list.add("s2");
        list.add("s3");
        list.add("s4");
        List list2 = new ArrayList<>();
        list2.add("s4");
        list2.add("s6");
        list2.add("s7");
        list2.add("s8");
        //将list2添加到list中


        String str = "123456,56895123";

        System.out.println(str.contains("56895123"));



    }
}
