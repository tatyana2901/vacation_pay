package com.example.vacation_pay.controller;

import com.example.vacation_pay.DTO.VacRequest;
import com.example.vacation_pay.DTO.VacResponse;
import com.example.vacation_pay.service.VacCalcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import jakarta.validation.Valid;

import java.text.NumberFormat;
import java.util.Locale;

@Controller
public class VacCalcController {

    @Autowired
    VacCalcService vacCalcService;

    @GetMapping("/")
    public String getCalculator(Model model) {

        model.addAttribute("vacRequest", new VacRequest()); //добавляем пустой объект VACDTO для отображения страницы
        return "vacCalculator";
    }


    @GetMapping("/calculate")
    public String getResult(Model model, @Valid @ModelAttribute("vacRequest") VacRequest vacRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "vacCalculator"; // Возвращаемся на форму с отображением ошибок валидации
        }
        VacResponse vacResponse = vacCalcService.getVacationPay(vacRequest);
        String formattedSalary = String.format("%.2f", vacResponse.getVacationSalary());
        model.addAttribute("vacationSalary", formattedSalary);
        return "vacCalculator";
    }


}
