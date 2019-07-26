package edu.xatu.util;

import net.sf.json.JSONObject;

public class JSONUtil {
    /**
     * 对象转json的方法
     * @return
     */
    public static String boj2json(Object obj){
        JSONObject ob=JSONObject.fromObject(obj);
        return ob.toString();
    }
    public static  <T> T json2obj(String jsonstr,Class<T> t){
        JSONObject object=JSONObject.fromObject(jsonstr);
        return (T)JSONObject.toBean(object,t);
    }
}
