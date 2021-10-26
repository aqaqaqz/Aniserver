package com.aniserver.api;

import com.aniserver.api.model.db.Directory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class DirectoryTest {

    @BeforeEach
    void setup(){}

    @ParameterizedTest
    @CsvSource(value = {
            "[Ohys-Raws] No Game No Life - 01 (AT-X 1280x720 x264 AAC).mp4:1",
            "[Ohys-Raws] No Game No Life - 100 (AT-X 1280x720 x264 AAC).mp4:100",
            "[Ohys-Raws] No Game No Life Zero Movie (BD 1920x1080 x264 FLACx2).mkv:-1"
    }, delimiter = ':')
    @DisplayName("에피소드 구하기")
    public void getEpsodeTest(String name, int episode){
        Directory d = Directory.builder()
                .name(name)
                .type("mov")
                .build();

        assertThat(episode).isEqualTo(d.getEpsode());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "[Ohys-Raws] No Game No Life - 01 (AT-X 1280x720 x264 AAC).mp4:No Game No Life",
            "[Ohys-Raws] No Game No Life Zero Movie (BD 1920x1080 x264 FLACx2).mkv:No Game No Life Zero Movie"
    }, delimiter = ':')
    @DisplayName("타이틀 구하기")
    public void getTitleTest(String name, String title){
        Directory d = Directory.builder()
                .name(name)
                .type("mov")
                .build();

        assertThat(title).isEqualTo(d.getTitle());
    }
}
