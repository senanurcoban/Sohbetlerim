package com.example.sohbetlerim.Utils;

import java.util.Random;

public class RandomName {
    public static String getSaltString(){
        String SALTCHARS="ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt=new StringBuilder();
        Random rnd=new Random();
        while (salt.length()<18){
            int index=(int)(rnd.nextFloat()*SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr=salt.toString();
        return saltStr;
    }
}

