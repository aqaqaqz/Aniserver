package com.aniserver.api;

import com.aniserver.api.model.Quarter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class QuarterTest {

    @BeforeEach
    void setup(){}

    @ParameterizedTest
    @CsvSource(value = {"2000:1:2000-1","2000:2:2000-1", "2000:3:2000-2", "2000:12:2001-1"}, delimiter = ':')
    @DisplayName("연도별 분기 구하기")
    public void getQuarterTest(int year, int month, String rst){
        Quarter q = new Quarter(year, month);
        assertThat(rst).isEqualTo(q.toString());
    }
}
