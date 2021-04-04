package com.ly.bigdata.utils;


import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;

import java.util.HashMap;
import java.util.Set;

public class SendMessageUtil {

    // 发送短信
    public static void sendMsg(String to, String[] datas) {
        //生产环境请求地址：app.cloopen.com
        String serverIp = "app.cloopen.com";
        //请求端口
        String serverPort = "8883";
        //主账号,登陆云通讯网站后,可在控制台首页看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN
        String accountSId = "8a216da878005a8001788afdbe2e33d4";
        String accountToken = "383cb94c92a04c219751b35e8e6b4575";
        //请使用管理控制台中已创建应用的APPID
        String appId = "8a216da878005a8001788afdbf1033da";
        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        sdk.init(serverIp, serverPort);
        sdk.setAccount(accountSId, accountToken);
        sdk.setAppId(appId);
        sdk.setBodyType(BodyType.Type_JSON);
        //String to = "18438705883";
        String templateId = "1";
        // String[] datas = {"博博你好呀，我是白敬亭！很荣幸成为你的idol！我会继续努力滴！期待有一天能和你见面吖~但是我们见面时，不许带上你的'好胖的烦人鬼'🤫，因为这是另外的价钱哈哈哈！", "一生之内♥", "变量3"};
        // String subAppend = "1234";  //可选 扩展码，四位数字 0~9999
        //  String reqId = "fadfafas";  //可选 第三方自定义消息id，最大支持32位英文数字，同账号下同一自然天内不允许重复
        //HashMap<String, Object> result = sdk.sendTemplateSMS(to,templateId,datas);
        HashMap<String, Object> result = sdk.sendTemplateSMS(to, templateId, datas, null, null);
        if ("000000".equals(result.get("statusCode"))) {
            //正常返回输出data包体信息（map）
            HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for (String key : keySet) {
                Object object = data.get(key);
                System.out.println(key + " = " + object);
            }
        } else {
            //异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
        }
    }
}


