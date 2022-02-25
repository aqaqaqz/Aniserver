package com.aniserver.api.controller;

import com.aniserver.common.Const;
import com.aniserver.common.exception.DirectoryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.security.auth.login.LoginException;

public class ExceptionController {
    @ExceptionHandler(LoginException.class)
    public ResponseEntity LoginException(Exception e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Const.MSG_AUTH_ERROR);
    }

    @ExceptionHandler(DirectoryException.class)
    public ResponseEntity DirectoryException(Exception e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
}
