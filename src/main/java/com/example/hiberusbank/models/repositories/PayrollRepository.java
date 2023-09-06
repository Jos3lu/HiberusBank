package com.example.hiberusbank.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hiberusbank.models.Payroll;

public interface PayrollRepository extends JpaRepository<Payroll, Long> {

}
