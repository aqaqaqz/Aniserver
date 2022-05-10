package com.aniserver.common.util.utils;

import java.util.Date;

public class DateUtil {
    public int getYear(){
        Date d = new Date();
        return d.getYear() + 1900;
    }

    public int getMonth(){
        Date d = new Date();
        return d.getMonth();
    }

    public String getYearAndQuarter(){
        int m = this.getMonth();
        int y = getYear();
        int q = 0;

        if(m == 12) y++;
        if(m==12 || (m>=1 && m<=2)) q = 1;
        if(m>=3 && m<=5) q = 2;
        if(m>=6 && m<=8) q = 3;
        if(m>=9 && m<=11) q = 4;

        return y+"-"+q;
    }
}
