package com.example.vacation_pay.controller;
import com.example.vacation_pay.DTO.VacRequest;
import com.example.vacation_pay.DTO.VacResponse;
import com.example.vacation_pay.service.VacCalcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import jakarta.validation.Valid;


@Controller
public class VacCalcController {

    @Autowired  //автоматическое создание связи с сервисом
    VacCalcService vacCalcService;

    @GetMapping("/")
    public String getCalculator(Model model) {
        model.addAttribute("vacRequest", new VacRequest()); //добавляем пустой объект VACDTO для отображения страницы
        return "vacCalculator";
    }

    @GetMapping("/calculate")
    public String getResult(Model model, @Valid @ModelAttribute("vacRequest") VacRequest vacRequest, BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {
            return "vacCalculator"; // Возвращаемся на форму с отображением ошибок валидации
        }
        VacResponse vacResponse = vacCalcService.getVacationPay(vacRequest); //считаем отпускные на основании данных полученного объекта vacRequest и тут же упаковываем результат расчета в объект - ответ vacResponse
        String formattedSalary = String.format("%.2f", vacResponse.getVacationSalary()); //сокращаем результат до 2 знаков после запятой
        model.addAttribute("vacationSalary", formattedSalary); //добавляем в модель полученный результат отпускных через реквизит html формы vacationSalary
        return "vacCalculator"; //возвращаем отображение html
    }


}
