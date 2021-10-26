package com.aniserver.api.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FileTest {

    final String name = "TestFolder";
    final String def = "./";
    final String fullPath = def + name;

    @BeforeEach
    void setup(){}

    @Test
    @DisplayName("폴더 crd 테스트")
    public void directoryCRDTest(){
        assertThat(Util.file.isExist(fullPath)).isFalse(); //처음은 무조건 없는상태

        Util.file.makeDirectory(def, name);
        assertThat(Util.file.isExist(fullPath)).isTrue(); //생성 후 확인

        Util.file.removeDirectory(fullPath);
        assertThat(Util.file.isExist(fullPath)).isFalse(); //삭제 후 확인
    }

    @Test
    @DisplayName("폴더 이동 테스트")
    public void directoryMoveTest(){
        final String movePath = def + "moveFolder";

        assertThat(Util.file.isExist(fullPath)).isFalse(); //처음은 무조건 없는상태

        Util.file.makeDirectory(def, name);
        Util.file.moveDirectory(fullPath, movePath);

        assertThat(Util.file.isExist(fullPath)).isFalse();
        assertThat(Util.file.isExist(movePath)).isTrue();

        Util.file.removeDirectory(movePath);
    }
}
