package com.example.sohbetlerim.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
public class GetDate {
    public static String getDate(){
        DateFormat df=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date today= Calendar.getInstance().getTime();
        String reportDate=df.format(today);
        return  reportDate;
    }
}
