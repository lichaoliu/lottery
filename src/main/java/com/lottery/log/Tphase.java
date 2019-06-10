package com.lottery.log;


import com.lottery.common.util.HTTPUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.Map;



/**
 * Created by fengqinyun on 15/8/25.
 */
public class Tphase {
    public static void main(String[] args) {
        String platyType="1001";
        System.out.println(platyType.endsWith("01"));
       //dd(null);
    }

    public  static void dd( Map<String, String> map){
        String strResult=null;

        try {

            JSONObject paramJson = new JSONObject();
            JSONArray paramJsonArray = new JSONArray();
            paramJsonArray.add("18600758958");
            paramJson.put("type", 2);
            paramJson.put("msg", "注册test");
            paramJson.put("numList", paramJsonArray);
            paramJson.put("clientType", "android");
            String send=paramJson.toString();
            System.out.println(send);
            strResult = HTTPUtil.postJson("http://118.26.65.147/smscenter/send/msg", send);
            System.out.println(strResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
