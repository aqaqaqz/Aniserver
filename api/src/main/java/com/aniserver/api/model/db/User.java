package com.aniserver.api.model.db;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Builder
public class User {
    private String id;
    private String passwd;
    private String lastLogin;
}
