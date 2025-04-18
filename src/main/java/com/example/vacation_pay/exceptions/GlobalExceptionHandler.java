package com.example.vacation_pay.exceptions;

import com.example.vacation_pay.DTO.VacRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public String handleException(Model model, IllegalDatesOrderException e) {

        model.addAttribute("error",e.getMessage()); //добавляем текст ошибки на форму
        model.addAttribute("vacRequest", new VacRequest()); //добавляем пустой объект ответа для отображения формы

        return "vacCalculator";




    }
}
