package com.cf.kindergarten.util;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

/**
 * hashmap中存的一个时间的键值,按照时间的值来倒序排列
 */
public class ComparatorUtil implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        int result = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            HashMap<String, String> map1 = (HashMap<String, String>) o1, map2 = (HashMap<String, String>) o2;
            Date map1_time = format.parse(map1.get("time"));
            Date map2_time = format.parse(map2.get("time"));
            if(map1_time.after(map2_time)){
                result = 1;
                }else if(map1_time.before(map2_time)){
                    result = -1;
                }else if(map1_time.equals(map2_time)){
                    result = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
