package com.aniserver.api.model;

import java.time.LocalDate;

public class Quarter {
    int year;
    int month;
    int quarter;

    // 11~1 : 1분기, 2~4 : 2분기, 5~7 : 3분기, 8~10 : 4분기
    // 3,6,9,12지만 전달 마지막주 방영이 있기 때문에 위와같이 계산 
    private void makeQuarter(){
        quarter = 1;

        if(month > 2) quarter++;
        if(month > 6) quarter++;
        if(month > 9) quarter++;
        if(month > 11){
            quarter = 1;
            year++;
        }
    }

    public Quarter(int year, int month){
        this.year = year;
        this.month = month;

        makeQuarter();
    }

    public Quarter(){
        LocalDate now = LocalDate.now();

        year = now.getYear();
        month = now.getMonthValue();

        makeQuarter();
    }

    @Override
    public String toString(){
        return ""+year+"-"+quarter;
    }
}
