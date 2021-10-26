package com.aniserver.api.util;

import com.aniserver.api.model.db.Directory;
import com.aniserver.api.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import static org.assertj.core.api.Assertions.assertThat;

public class FileTest {

    @BeforeEach
    void setup(){}

    @Test
    @DisplayName("폴더 crud 테스트")
    public void directoryCRUDTest(){
        String name = "TestFolder";
        String fullPath = "c:/test/" + name;

        assertThat(Util.file.isExist(fullPath)).isFalse(); //처음은 무조건 없는상태

        Util.file.makeDirectory("c:/test/", name);
        assertThat(Util.file.isExist(fullPath)).isTrue(); //생성 후 확인

        Util.file.removeDirectory(fullPath);
        assertThat(Util.file.isExist(fullPath)).isFalse(); //삭제 후 확인
    }
}
