package com.aniserver.api.exception;

import com.aniserver.api.model.db.User;

public class UserException extends RuntimeException{
    public UserException(String msg) {
        super(msg);
    }
}
