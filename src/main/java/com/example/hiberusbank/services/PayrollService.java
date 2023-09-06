package com.example.hiberusbank.services;

import com.example.hiberusbank.models.Payroll;

public interface PayrollService {

	/**
	 * Pay salary to workers
	 * 
	 * @param workerId Worker ID
	 * @return Payroll
	 */
	Payroll paySalary(Long workerId);

}
