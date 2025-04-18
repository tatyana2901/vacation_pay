package com.example.vacation_pay.service;

import com.example.vacation_pay.DTO.VacRequest;
import com.example.vacation_pay.DTO.VacResponse;
import com.example.vacation_pay.exceptions.IllegalDatesOrderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class VacCalcService {


    @Autowired
    HolidayService holidayService;
    private static final double AVG_DAYS_IN_MONTH = 29.3;
    private static final int MONTHS_IN_YEAR = 12;

    public VacResponse getVacationPay(VacRequest vacRequest) {

        if (vacRequest.getStartDate().isAfter(vacRequest.getEndDate())) {
            throw new IllegalDatesOrderException("День начала отпуска не может быть позже дня его окончания!");
        }
        double avgDaySalary = vacRequest.getSalary() / (MONTHS_IN_YEAR * AVG_DAYS_IN_MONTH); //считаем средненевной заработок
        double vacationSalary = avgDaySalary * getCountOfPaidDays(vacRequest.getStartDate(), vacRequest.getEndDate());//получаем итоговую сумму отпускных
        return new VacResponse(vacationSalary);

    }

    public long getCountOfPaidDays(LocalDate start, LocalDate end) { //количество дней к оплате
        return start.datesUntil(end.plusDays(1)) //поток из дат между start и end включительно
                .filter(d -> !holidayService.isHoliday(d))  //убираем праздничные дни
                .count(); //считаем количество элементов в потоке
    }

}
