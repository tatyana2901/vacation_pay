package com.example.vacation_pay.DTO;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class VacRequest {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;
    private double salary;

    public VacRequest() {
    }


    public VacRequest(LocalDate startDate, LocalDate endDate, double salary) {
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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }


}
