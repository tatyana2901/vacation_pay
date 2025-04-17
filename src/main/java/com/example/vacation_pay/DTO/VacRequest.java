package com.example.vacation_pay.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class VacRequest {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "Внесите дату начала отпуска!")
    private LocalDate startDate;

    @NotNull(message = "Внесите дату окончания отпуска!")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @NotNull(message = "Внесите данные о зарплате!")
    @Min(message = "Зарплата не может быть отрицательным числом", value = 0)
    private Double salary;


    public VacRequest() {
    }


    public VacRequest(LocalDate startDate, LocalDate endDate, Double salary) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.salary = salary;
    }


    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }


}
