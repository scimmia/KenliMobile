package com.jiayusoft.mobile.kenli.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by ASUS on 2014/7/1.
 */
public interface GlobalData {

    public static final String versionCode = "versioncode";
    public static final String softName = "softname";
    public static final String defaultSoftName = "doctor";
    public static final String defaultNetErrorMsg = "网络连接错误，请检查网络或服务地址设置。";


    public static final String defaultServerUrl = "221.214.98.55:8880/mobile";
    public static final String innerServerUrl = "11.0.0.55:8880/mobile";
    public static final String serverUrl = "serverUrl";

    public static final String dbInited = "dbInited";
    public static final String dbVersion = "dbVersion";
    public static final int dbCurrentVersion = 1;

    public static final String XMLBody = "XMLBody";
    public static final String JsonBody = "JsonBody";
    public static final String LOGIN_USER_NAME = "LOGIN_USER_NAME";
    public static final String LOGIN_PASSWORD = "LOGIN_PASSWORD";
    public static final String LOGIN_SAVE_PASSWORD = "LOGIN_SAVE_PASSWORD";
    public static final String loginAutoLogin = "loginAutoLogin";
    public static final String loginSuoshuJigouName = "loginSuoshuJigouName";
    public static final String loginSuoshuJigouID = "loginSuoshuJigouID";

    public static final int search_time_result = 100;
    public static final String searchBeginTime = "searchBeginTime";
    public static final String searchEndTime = "searchEndTime";

    public static final String itemTitle = "itemTitle";
    public static final String itemType = "itemType";
    public static final int searchTypeCheck = 1;
    public static final int searchTypeBorrow = 2;

    public static final int cardImageStar = 1;
    public static final int cardImageBorrow = 2;
    public static final int cardImageNull = 3;
    public static final int cardImageNotYet = 4;
    public static final int cardImageAccepted = 5;
    public static final int cardImageRefused = 6;
    public static final int cardImageQuality = 7;

    public static final int cardClickEvent = 1;
    public static final int cardImageEvent = 2;

    public static final int httpGet = 1;
    public static final int httpPost = 2;

    public static final int searchBorrowLoadMore = 3;

    public static final int tagLogin = 1;
//    String loginUrl = "/user/login/doctor";
    String loginUrl = "/user/login/doctorwithimages";
    public static final String loginUserID = "userid";
    public static final String loginPassword = "password";
    public static final String loginOrgcode = "orgcode";

    public static final int tagCheckUpdate = 2;
    String checkUpdateUrl = "/user/checkUpdate";

    public static final int tagDownloadNewFile = 3;

    public static final int tagGetOrgInfo = 4;
    String getOrgInfoUrl = "/user/orginfo";

    String logoImgUrl = "Logo/%s";//fileName



    int tagSearchLoadMore = 11;
    String searchLoadMoreUrl = "/bingan/doctor/list";
    String searchBorrowLoadMoreUrl = "/bingan/borrowlist";
    int tagSearchQualityLoadMore = 12;
    String searchQualityLoadMoreUrl = "/bingan/qualityneedrepair";
//    String searchQualityLoadMoreUrl = "/bingan/qualitystates";

    int tagBinganDetail = 13;
    String binganDetailUrl = "/bingan/doctor/detail/%s/%s";
    String binganDetailImageUrl = "Doc/%s";//fileName

    int tagBorrowBingan = 14;
    String borrowBinganUrl = "/bingan/borrow";

    int tagSearchStatesLoadMore = 15;
    String searchBorrowStatesLoadMoreUrl = "/bingan/borrowstates";




    String baseFolder = Environment.getExternalStorageDirectory().getPath()+ File.separator+"JiayuSoft"+ File.separator;
    String updateFolder = baseFolder + "update" + File.separator;


    public static final String WS_NameSpace = "http://com.zljy.oa.webservice";
    public static final String SERVICE_URL = "http://113.128.228.118:9090/oa/ws/Wis4vWebServices";
//    public static final String SERVICE_URL = "http://113.128.228.118:9090/oa/ws/ImpData";
    public static final String WS_Method_getXianinfo = "getXianinfo";
    public static final String WS_Method_getJdinfo = "getJdinfo";
    public static final String WS_Method_getJwhinfo = "getJwhinfo";

    public static final String WS_Method_getYlfninfo = "getYlfninfo";

    public static final String WS_Property_Binding = "Request";
    public static final String WS_Property_Save = "Request";

    String[] shifouNames = { "是",  "否",  };
    String[] shifouIDs = { "1",  "0",  };

    String[] hunyinzhuangkuangNames = { "未婚",  "已婚",  "初婚",  "再婚",  "复婚",  "丧偶",  "离婚",  "其他",  };
    String[] hunyinzhuangkuangIDs = { "10",  "20",  "21",  "22",  "23",  "30",  "40",  "90",  };

    String[] biyuncuoshiNames = { "口服避孕药",  "注射避孕针",  "皮下埋植避孕剂",  "阴道环",  "外用避孕药—杀精剂",  "屏障避孕器具",  "宫内节育器",  "绝育术",  "女性绝育术",  "男性绝育术",  "其他避孕方法",  "自然避孕法",  "未避孕原因",  "待孕",  "现孕",  "绝经（包括子宫切除）",  "子宫切除",  "其他未避孕原因",  "有病不宜",  "产后一月",  "不孕症",  "离婚、丧偶",  "外出躲藏",  "情况不明",  "取出皮下埋植避孕剂",  "女性绝育复通术",  "男性绝育复通术",  "12周以内人工流产",  "12周以上人工流产",  "脱环",  };
    String[] biyuncuoshiIDs = { "100",  "200",  "300",  "400",  "500",  "600",  "700",  "800",  "810",  "820",  "900",  "910",  "A00",  "A10",  "A20",  "A30",  "A31",  "A90",  "A91",  "A92",  "A93",  "A94",  "A95",  "A96",  "B10",  "D10",  "D20",  "E10",  "E20",  "F10",  };

    String[] fangshijiluNames = { "第1次",  "第2次",  "第3次",  "第4次",  "第5次",  "第6次",  "第7次",  "第8次",  "第9次",  };
    String[] fangshijiluIDs = { "1",  "2",  "3",  "4",  "5",  "6",  "7",  "8",  "9",  };

    String[] shengyuqingkuangNames = { "已生育",  "未生育",  };
    String[] shengyuqingkuangIDs = { "1",  "2",  };

    String[] haiciNames = { "一男孩",  "一女孩",  "一男一女",  "两男孩",  "两女孩",  };
    String[] haiciIDs = { "1",  "2",  "3",  "4",  "5",  };

}
