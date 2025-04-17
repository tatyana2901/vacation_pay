package com.example.vacation_pay.service;

import com.example.vacation_pay.DTO.VacRequest;
import com.example.vacation_pay.DTO.VacResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VacCalcService {

    @Autowired
    HolidayService holidayService;

    public VacResponse getVacationPay(VacRequest vacRequest) {




        double vacationSalary = 15000000.98;
        return new VacResponse(vacationSalary);
    }

}
