package com.example.vacation_pay.DTO;

public class VacResponse {

    private Double vacationSalary;

    public VacResponse() {
    }


    public VacResponse(Double vacationSalary) {
        this.vacationSalary = vacationSalary;
    }


    public Double getVacationSalary() {
        return vacationSalary;
    }

    public void setVacationSalary(Double vacationSalary) {
        this.vacationSalary = vacationSalary;
    }
}
