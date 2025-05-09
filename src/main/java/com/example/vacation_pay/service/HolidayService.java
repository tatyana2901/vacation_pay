package com.example.vacation_pay.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class HolidayService {
    @Value("${holidays.file.name}") //название файла с праздниками указано в файле app.properties
    private String HOLIDAYS_FILE; // Файл в src/main/resources
    private final DateTimeFormatter INPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/y");

    private List<LocalDate> holidays = new ArrayList<>();

   // @PostConstruct //метод выполняется сразу после создания объекта HolidayService только 1 раз
    public void loadHolidays() {
        try (BufferedReader bfr = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(HOLIDAYS_FILE)))) {//загрузка данных о праздниках из файла внутри проекта
            String line;
            while ((line = bfr.readLine()) != null) {
                LocalDate holiday = LocalDate.parse(line, INPUT_DATE_FORMAT);
                holidays.add(holiday); //парсим и добавляем даты в список holidays
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Cacheable("holidays") //демонстрация кэширования
    public List<LocalDate> getHolidays() {
        return holidays;
    }

    public boolean isHoliday(LocalDate day) {
        return holidays.stream().anyMatch(x -> x.equals(day));
    } //проверка даты - является ли она праздником
}











