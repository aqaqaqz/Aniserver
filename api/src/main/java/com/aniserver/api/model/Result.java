package com.aniserver.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {
    private int totalCnt;
    private int successCnt;
    private String msg;

    public Result(){}

    public Result(int t, int s){
        totalCnt = t;
        successCnt = s;
    }

    public Result(int t, int s, String m){
        totalCnt = t;
        successCnt = s;
        msg = m;
    }
}
