package com.aniserver.api.service;

import com.aniserver.api.model.Directory;
import com.aniserver.api.util.Const;
import com.aniserver.api.util.Util;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class DirectoryServiceTest {

    final static String quarter = "2000-1";
    final static String existName = "existName";
    final static String notExistName = "notExistName";

    private static DirectoryService service;

    @BeforeAll
    static void setup(){
        service = new DirectoryService();
        Util.file.makeDirectory(Const.DEFAULT_PATH, quarter);
        Util.file.makeDirectory(Const.DEFAULT_PATH+"/"+quarter, existName);
    }

    @AfterAll
    static void end(){
        Util.file.removeDirectory(Const.DEFAULT_PATH+"/"+quarter+"/"+existName);
        Util.file.removeDirectory(Const.DEFAULT_PATH+"/"+quarter);
    }

    @Test
    @DisplayName("타이틀 쿼터 구하기")
    public void getQuarterByTitleTest(){
        assertThat(service.getQuarterByTitle(existName)).isEqualTo(quarter);
        assertThat(service.getQuarterByTitle(notExistName)).isEqualTo(Const.EMPTY);
    }
}
