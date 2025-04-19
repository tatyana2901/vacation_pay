package com.example.vacation_pay.exceptions;

import com.example.vacation_pay.DTO.VacRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalDatesOrderException.class) //исключение в случае если порядок дат нарушен
    public String handleException(Model model, IllegalDatesOrderException e) {
        model.addAttribute("error", e.getMessage()); //добавляем текст ошибки на форму
        model.addAttribute("vacRequest", new VacRequest()); //добавляем пустой объект ответа для отображения формы
        return "vacCalculator";
    }

    @ExceptionHandler //общее исключение
    public String handleAnyException(Model model, Exception e) { //для любых непредусмотренных ошибок
        model.addAttribute("error", e.getMessage()); //добавляем текст ошибки на форму
        return "errors";
    }


}
