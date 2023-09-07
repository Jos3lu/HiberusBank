package com.example.hiberusbank.services;

import java.util.List;

import com.example.hiberusbank.models.Payroll;

public interface PayrollService {

	/**
	 * Pay salary to workers
	 * 
	 * @param workerId Worker ID
	 * @return Payroll
	 */
	Payroll paySalary(Long workerId);

	/**
	 * Get salaries of all the workers
	 * 
	 * @return List<Payroll>
	 */
	List<Payroll> getPayrolls();

}
