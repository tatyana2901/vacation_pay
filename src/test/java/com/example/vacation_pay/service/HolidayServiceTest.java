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
    @InjectMocks //создаем реальный объект holidayservice и внедряем туда @spy
    private HolidayService holidayService;

    private final String FILE_NAME_TEST = "test_holidays.csv";

    @BeforeEach
    void createTestHolidayFile() {
        Path resourcePath = Path.of("src", "test", "resources", FILE_NAME_TEST);
        try (FileWriter fileWriter = new FileWriter(resourcePath.toString())) {
            fileWriter.write("01/01/2025\n");
            fileWriter.write("07/05/2025\n");
            fileWriter.write("02/06/2025\n");
            fileWriter.write("15/09/2025\n");
            fileWriter.write("01/10/2025\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        ReflectionTestUtils.setField(holidayService, "HOLIDAYS_FILE", FILE_NAME_TEST);
        holidayService.loadHolidays();
        System.out.println(holidayService.getHolidays());

    }

   /* @AfterEach
    void deleteTestHolidayFile() {

        try {
            Files.delete(Path.of(FILE_NAME_TEST));

            System.out.println("УДАЛЕНИЕ");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }*/

    @Test
    void successfulFileLoad() {
        List<LocalDate> holidays = holidayService.getHolidays();
        assertFalse(holidays.isEmpty());
        assertEquals(LocalDate.of(2025, 1, 1), holidays.getFirst());
    }
   /* @Test
    void loadHolidays() {
    }

    @Test
    void isHoliday() {
    }*/
}