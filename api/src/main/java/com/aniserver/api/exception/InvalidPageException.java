package com.aniserver.api.exception;

public class InvalidPageException extends Exception {
    public InvalidPageException(String m){
        super(m);
        System.out.println("PageException[" + m + "]");
    }
}
