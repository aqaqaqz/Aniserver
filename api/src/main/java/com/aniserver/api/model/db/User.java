package com.aniserver.api.model.db;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class User {
    private String id;
    private String passwd;
    private String lastLogin;
}
