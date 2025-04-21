package com.example.vacation_pay.service;

import com.example.vacation_pay.DTO.VacRequest;
import com.example.vacation_pay.DTO.VacResponse;
import com.example.vacation_pay.exceptions.IllegalDatesOrderException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
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
    @InjectMocks // создаем реальный объект(класс) и внедряем туда @Mock
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
    void getVacationPay_HolidaysIncluded_OK() {
        VacRequest vacRequest = new VacRequest(LocalDate.of(2025, 4, 6), LocalDate.of(2025, 4, 10), 5000.0); //Создаем имитацию запроса на расчет
        when(holidayService.isHoliday(LocalDate.of(2025, 4, 6))).thenReturn(false); //настраиваем каждый день из периода - является праздником или нет
        when(holidayService.isHoliday(LocalDate.of(2025, 4, 7))).thenReturn(true);
        when(holidayService.isHoliday(LocalDate.of(2025, 4, 8))).thenReturn(true);
        when(holidayService.isHoliday(LocalDate.of(2025, 4, 9))).thenReturn(false);
        when(holidayService.isHoliday(LocalDate.of(2025, 4, 10))).thenReturn(false);

        VacResponse vacResponse = vacCalcService.getVacationPay(vacRequest);//получаем объект vacRequest с введенными выше параметрами
        double salaryExpected = 5000.0 / 12 / 29.3 * 3; //считаем вручную результат , который является правильным для введенных данных
        assertEquals(salaryExpected, vacResponse.getVacationSalary(), 0.01); //проверяем равенство зарплаты ожидаемой и той, которая должна получиться
    }

    @Test
    void getVacationPay_OnlyHolidays_OK() {
        VacRequest vacRequest = new VacRequest(LocalDate.of(2025, 4, 6), LocalDate.of(2025, 4, 8), 5000.0); //Создаем имитацию запроса на расчет
        when(holidayService.isHoliday(LocalDate.of(2025, 4, 6))).thenReturn(true); //настраиваем каждый день из периода - является праздником или нет
        when(holidayService.isHoliday(LocalDate.of(2025, 4, 7))).thenReturn(true);
        when(holidayService.isHoliday(LocalDate.of(2025, 4, 8))).thenReturn(true);

        VacResponse vacResponse = vacCalcService.getVacationPay(vacRequest);//получаем объект vacRequest с введенными выше параметрами
        double salaryExpected = 5000.0 / 12 / 29.3 * 0; //считаем вручную результат , который является правильным для введенных данных
        assertEquals(salaryExpected, vacResponse.getVacationSalary(), 0.01); //проверяем равенство зарплаты ожидаемой и той, которая должна получиться
    }

    @Test
    void getVacationPay_OneDayOnly_OK() {
        VacRequest vacRequest = new VacRequest(LocalDate.of(2025, 4, 6), LocalDate.of(2025, 4, 6), 5000.0); //Создаем имитацию запроса на расчет
        when(holidayService.isHoliday(LocalDate.of(2025, 4, 6))).thenReturn(false); //настраиваем каждый день из периода - является праздником или нет

        VacResponse vacResponse = vacCalcService.getVacationPay(vacRequest);//получаем объект vacRequest с введенными выше параметрами
        double salaryExpected = 5000.0 / 12 / 29.3 * 1; //считаем вручную результат , который является правильным для введенных данных
        assertEquals(salaryExpected, vacResponse.getVacationSalary(), 0.01); //проверяем равенство зарплаты ожидаемой и той, которая должна получиться
    }

    @Test
    void getVacationPay_IncorrectDateOrder_OK() {
        VacRequest vacRequest = new VacRequest(LocalDate.of(2025, 4, 6), LocalDate.of(2025, 4, 1), 5000.0); //Имитируем запрос с неправильным порядком дат - начало отпуска позже окончания
        assertThrows(IllegalDatesOrderException.class, () -> vacCalcService.getVacationPay(vacRequest)); //проверяем, что при попытке вызвать метод getVacationPay с неправильным порядком дат возникнет исключение

    }

    @Test
    void getCountOfPaidDays_OK1() {

        LocalDate start = LocalDate.of(2025, 4, 1);
        LocalDate end = LocalDate.of(2025, 4, 3);
        when(holidayService.isHoliday(LocalDate.of(2025, 4, 1))).thenReturn(false); //все дни рабочие
        when(holidayService.isHoliday(LocalDate.of(2025, 4, 2))).thenReturn(false);
        when(holidayService.isHoliday(LocalDate.of(2025, 4, 3))).thenReturn(false);

        assertEquals(3, vacCalcService.getCountOfPaidDays(start, end)); //проверяем соответствие результата метода getCountOfPaidDays правильному значению

    }

    @Test
    void getCountOfPaidDays_OK2() {

        LocalDate start = LocalDate.of(2025, 4, 1);
        LocalDate end = LocalDate.of(2025, 4, 3);
        when(holidayService.isHoliday(LocalDate.of(2025, 4, 1))).thenReturn(true); //два дня праздники, 1 день рабочий
        when(holidayService.isHoliday(LocalDate.of(2025, 4, 2))).thenReturn(true);
        when(holidayService.isHoliday(LocalDate.of(2025, 4, 3))).thenReturn(false);

        assertEquals(1, vacCalcService.getCountOfPaidDays(start, end)); //проверяем соответствие результата метода getCountOfPaidDays правильному значению

    }
}