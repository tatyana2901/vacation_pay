package com.example.vacation_pay.service;

import com.example.vacation_pay.DTO.VacRequest;
import com.example.vacation_pay.DTO.VacResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class) //запуск мокито, инициализация моков
class VacCalcServiceTest {
    @Mock //создает искусственный объект-заглушку holidayService, изолируя тестирование calcService
    private HolidayService holidayService;
    @InjectMocks // автоматически добавляет объект холидэйсервис в объект ваккальксервис
    private VacCalcService vacCalcService;

    @Test
    void getVacationPay_NoHolidays_OK() {
        VacRequest vacRequest = new VacRequest(LocalDate.of(2025, 4, 6), LocalDate.of(2025, 4, 10), 5000.0); //Создаем имитацию запроса на расчет
        when(holidayService.isHoliday(LocalDate.of(2025, 4, 6))).thenReturn(false); //настраиваем каждый день из периода - является праздником или нет
        when(holidayService.isHoliday(LocalDate.of(2025, 4, 7))).thenReturn(false);
        when(holidayService.isHoliday(LocalDate.of(2025, 4, 8))).thenReturn(false);
        when(holidayService.isHoliday(LocalDate.of(2025, 4, 9))).thenReturn(false);
        when(holidayService.isHoliday(LocalDate.of(2025, 4, 10))).thenReturn(false);

        VacResponse vacResponse = vacCalcService.getVacationPay(vacRequest);//получаем объект vacRequest с введенными выше параметрами
        double salaryExpected = 5000.0 / 12 / 29.3 * 5; //считаем вручную результат , который является правильным для введенных данных
        assertEquals(salaryExpected, vacResponse.getVacationSalary()); //проверяем равенство зарплаты ожидаемой и той, которая должна получиться
    }

    @Test
    void getCountOfPaidDays() {
    }
}