package com.jiayusoft.mobile.kenli.utils.io;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Hi on 2016-5-5.
 */
public class PrintNameIDs {
    static String title = "haici";
    static String content = "1  一男孩\n" +
            "2  一女孩\n" +
            "3  一男一女\n" +
            "4  两男孩\n" +
            "5  两女孩\n";
//    static String sp = "\t";
    static String sp = " ";
    static void printNameIDs(){
        String[] items = StringUtils.split(content,"\n");
        String nameTemp = "String[] "+title+"Names = {";
        String idTemp = "String[] "+title+"IDs = {";
        for (String item : items){
//            System.out.println(item);
            String[] temp = StringUtils.split(item,sp);
            if (temp!= null && temp.length == 2){
                nameTemp += " \""+temp[1]+"\", ";
                idTemp += " \""+temp[0]+"\", ";
            }
        }
        nameTemp += " }; ";
        idTemp += " }; ";
        System.out.println(nameTemp);
        System.out.println(idTemp);
    }


    static String ids = "sp_shequ";
    static String codes = "\t@OnFocusChange(R.id.change)\n" +
            "    void onchangeFocus(boolean focused) {\n" +
            "        if (focused) {\n" +
            "            onchange();\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    @OnClick(R.id.change)\n" +
            "    void onchange() {\n" +
            "        DebugLog.e(\"onchange\");\n" +
            "        \n" +
            "    }\n";
    static void printClicks() {
        System.out.println(StringUtils.replace(codes,"change",ids));
    }

    public static void main(String[] args) {
        printClicks();
    }
}
