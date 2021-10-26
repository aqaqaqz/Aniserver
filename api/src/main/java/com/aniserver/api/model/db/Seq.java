package com.aniserver.api.model.db;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Seq {
    private String name;
    private int next;
}
