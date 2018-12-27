package com.wang.myandroid.utils;

/**
 * Created by MaxWang on 2018/12/5.
 * 项目名称：MyAndroid
 * 类描述  ：
 * 创建人  ：MaxWang
 * 创建时间：2018/12/5 14:11
 * 修改人  ：MaxWang
 * 修改时间：2018/12/5
 * 修改备注：
 */

public class StaticClass {
    //sharedpreference的
    public static final String NAME= "config";
    //欢迎页延时
    public static final int HANDLER_SPLASH = 1001;
    //判断程序是否第一次运行
    public static final String SHARE_IS_FIRST = "isFirst";
    //腾讯bugly的id
    public static final String BUGLY_APP_ID = "26e979ab66";
    //Bmob key
    public static final String BMOB_APP_ID = "752808db335cdce2aea8781721ccd04d";

    //快递Key
    public static final String COURIER_KEY = "1af43b4fd1c9ecf97493233f6769b749";

    //归属地Key  可以用
    public static final String PHONE_KEY = "22a6ba14995ce26dd0002216be51dabb";

    //问答机器人key
    public static final String CHAT_LIST_KEY = "7a48539921338ef90866922b21e25f6d";
    //微信精选key
    public static final String WECHAT_KEY = "78f723dccf85aea324a3cf0daac97f35";

    //妹子接口
    public static final String GIRL_URL = "";

    //语音Key
    public static final String VOICE_KEY = "583081c6";

    //短信Action
    public static final String SMS_ACTION = "android.provider.Telephony.SMS_RECEIVED";

    //版本更新   自己Tomcat地址
    public static final String CHECK_UPDATE_URL = "http://192.168.1.23:8080/liuguilin/config.json";
/*    {
        "resultcode": "200", *//* 老版状态码，新用户请忽略此字段 *//*
            "reason": "查询物流信息成功",
            "result": {
        "company": "EMS", *//* 快递公司名字 *//*
                "com": "ems",
                "no": "1186465887499", *//* 快递单号 *//*
                "status": "1", *//* 1表示此快递单的物流信息不会发生变化，此时您可缓存下来；0表示有变化的可能性 *//*
                "list": [
        {
            "datetime": "2016-06-15 21:44:04",  *//* 物流事件发生的时间 *//*
                "remark": "离开郴州市 发往长沙市【郴州市】", *//* 物流事件的描述 *//*
                "zone": "" *//* 快件当时所在区域，由于快递公司升级，现大多数快递不提供此信息 *//*
        },
        {
            "datetime": "2016-06-15 21:46:45",
                "remark": "郴州市邮政速递物流公司国际快件监管中心已收件（揽投员姓名：侯云,联系电话:）【郴州市】",
                "zone": ""
        },
        {
            "datetime": "2016-06-16 12:04:00",
                "remark": "离开长沙市 发往贵阳市（经转）【长沙市】",
                "zone": ""
        },
        {
            "datetime": "2016-06-17 07:53:00",
                "remark": "到达贵阳市处理中心（经转）【贵阳市】",
                "zone": ""
        },
        {
            "datetime": "2016-06-18 07:40:00",
                "remark": "离开贵阳市 发往毕节地区（经转）【贵阳市】",
                "zone": ""
        },
        {
            "datetime": "2016-06-18 09:59:00",
                "remark": "离开贵阳市 发往下一城市（经转）【贵阳市】",
                "zone": ""
        },
        {
            "datetime": "2016-06-18 12:01:00",
                "remark": "到达  纳雍县 处理中心【毕节地区】",
                "zone": ""
        },
        {
            "datetime": "2016-06-18 17:34:00",
                "remark": "离开纳雍县 发往纳雍县阳长邮政支局【毕节地区】",
                "zone": ""
        },
        {
            "datetime": "2016-06-20 17:55:00",
                "remark": "投递并签收，签收人：单位收发章 *【毕节地区】",
                "zone": ""
        }
    ]
    },
        "error_code": 0 *//* 错误码，0表示查询正常，其他表示查询不到物流信息或发生了其他错误 *//*
    }*/


   public static final String JSON_STRING =  "{        \"resultcode\":\"200\",            \"reason\":\"查询物流信息成功\",            \"result\":{        \"company\":\"EMS\",                \"com\":\"ems\",                \"no\":\"1186465887499\",                \"status\":\"1\",                \"list\":[        {            \"datetime\":\"2016-06-15 21:44:04\",                \"remark\":\"离开郴州市 发往长沙市【郴州市】\",                \"zone\":\"郴州市\"        },        {            \"datetime\":\"2016-06-15 21:46:45\",                \"remark\":\"郴州市邮政速递物流公司国际快件监管中心已收件（揽投员姓名：侯云,联系电话:）【郴州市】\",                \"zone\":\"郴州市\"        },        {            \"datetime\":\"2016-06-16 12:04:00\",                \"remark\":\"离开长沙市 发往贵阳市（经转）【长沙市】\",                \"zone\":\"长沙市\"        },        {            \"datetime\":\"2016-06-17 07:53:00\",                \"remark\":\"到达贵阳市处理中心（经转）【贵阳市】\",                \"zone\":\"贵阳市\"        },        {            \"datetime\":\"2016-06-18 07:40:00\",                \"remark\":\"离开贵阳市 发往毕节地区（经转）【贵阳市】\",                \"zone\":\"贵阳市\"        },        {            \"datetime\":\"2016-06-18 09:59:00\",                \"remark\":\"离开贵阳市 发往下一城市（经转）【贵阳市】\",                \"zone\":\"贵阳市\"        },        {            \"datetime\":\"2016-06-18 12:01:00\",                \"remark\":\"到达 纳雍县 处理中心【毕节地区】\",                \"zone\":\"纳雍县\"        },        {            \"datetime\":\"2016-06-18 17:34:00\",                \"remark\":\"离开纳雍县 发往纳雍县阳长邮政支局【毕节地区】\",                \"zone\":\"毕节地区\"        },        {            \"datetime\":\"2016-06-20 17:55:00\",                \"remark\":\"投递并签收，签收人：单位收发章 *【毕节地区】\",                \"zone\":\"快递已签收\"        }]    },        \"error_code\":0    }";
}
