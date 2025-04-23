package com.example.vacation_pay.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HolidayServiceTest {
    @Spy //mock с возможностью корректировать его поведение там где это необходимо
    @InjectMocks //создаем реальный объект holidayService и внедряем туда @spy
    private HolidayService holidayService;
    static Path resourcePath = Path.of("src", "test", "resources", "test_holidays.csv");


    @BeforeEach
    void doFirst() throws InterruptedException {

        ReflectionTestUtils.setField(holidayService, "HOLIDAYS_FILE", resourcePath.getFileName().toString()); //подменяем строку с названием файла оригинального
        holidayService.loadHolidays();
    }


    @Test
    void loadHolidaysTestOk(){ //проверяем, что список праздников подгрузился из файла правильно
        List<LocalDate> holidays = holidayService.getHolidays();
        assertFalse(holidays.isEmpty());
        assertEquals(LocalDate.of(2025, 1, 1), holidays.getFirst());
        assertEquals(LocalDate.of(2025, 5, 7), holidays.get(1));
        assertEquals(LocalDate.of(2025, 6, 2), holidays.get(2));
        assertEquals(LocalDate.of(2025, 9, 15), holidays.get(3));
        assertEquals(LocalDate.of(2025, 10, 1), holidays.get(4));

    }


    @Test
    void isHoliday_true() { //проверяем работу метода isHoliday
        assertTrue(holidayService.isHoliday(LocalDate.of(2025, 6, 2)));
    }

    @Test
    void isHoliday_false() {//проверяем работу метода isHoliday
        assertFalse(holidayService.isHoliday(LocalDate.of(2025, 6, 5)));
    }

    @Test
    void isHoliday_false1() {//проверяем работу метода isHoliday
        assertFalse(holidayService.isHoliday(LocalDate.of(2025, 9, 16)));
    }
}