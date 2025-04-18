package com.example.vacation_pay.service;

import com.example.vacation_pay.DTO.VacRequest;
import com.example.vacation_pay.DTO.VacResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.function.Consumer;

@Service
public class VacCalcService {

    @Autowired
    HolidayService holidayService;

    public VacResponse getVacationPay(VacRequest vacRequest) {
        double vacationSalary = 0;

/*
        if (vacRequest.getStartDate().isAfter(vacRequest.getEndDate())) {


                throw new Exception("День начала отпуска не может быть позже дня его окончания!");


        } else if (vacRequest.getSalary() == 0) {

            try {
                throw new Exception("Заработная плата не может быть пустой!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } else {*/
            double avgDaySalary = vacRequest.getSalary() / (12 * 29.3); //считаем средненевной заработок
            vacationSalary = avgDaySalary * getCountOfPaidDays(vacRequest.getStartDate(), vacRequest.getEndDate());//получаем итоговую сумму отпускных



        return new VacResponse(vacationSalary);
    }

    public long getCountOfPaidDays(LocalDate start, LocalDate end) { //количество дней к оплате
        return start.datesUntil(end.plusDays(1)) //поток из дат между start и end включительно
                .filter(d -> !holidayService.isHoliday(d))  //убираем праздничные дни
                .count(); //считаем количество элементов в потоке
    }

}
