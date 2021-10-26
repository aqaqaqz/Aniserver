package com.aniserver.api.exception;

public class EmptyParamException extends RuntimeException{
    public EmptyParamException(String daoName, String columnName) {
        super("KeyException : dao["+daoName+"] column["+columnName+"]");
    }
}
