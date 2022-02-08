package com.aniserver.common.exception;

import lombok.Getter;

@Getter
public class DirectoryException extends RuntimeException{

    private int code;

    public DirectoryException(String msg, int code){
        super(msg);
        this.code = code;
    }

}
