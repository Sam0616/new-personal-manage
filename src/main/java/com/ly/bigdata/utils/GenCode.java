package com.ly.bigdata.utils;

public class GenCode {

    // 9HJFJHRSU   生成注册码
    private static String str="1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String gen(){
        StringBuffer sb =new StringBuffer();
        // 03~5
        for (int i = 0; i <9 ; i++) {

            char c = str.charAt((int) Math.floor(Math.random() * 36));
            sb.append(c);
        }
        return sb.toString();
    }

    public static void main(String args[]){
        System.out.println(GenCode.gen());
    }
}
