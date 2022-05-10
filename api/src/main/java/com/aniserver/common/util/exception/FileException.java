package com.aniserver.common.util.exception;

public class FileException extends RuntimeException {
    public FileException(String m){
        super(m);
        System.out.println("FileException[" + m + "]");
    }
}
