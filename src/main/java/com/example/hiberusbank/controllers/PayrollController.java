package com.example.hiberusbank.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hiberusbank.models.Payroll;
import com.example.hiberusbank.models.views.PayrollViews;
import com.example.hiberusbank.services.PayrollService;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/api")
public class PayrollController {

	@Autowired
	private PayrollService payrollService;
	
	@PostMapping("/workers/{workerId}/payroll")
	@JsonView(PayrollViews.BasicData.class)
	public ResponseEntity<Payroll> paySalary(@PathVariable Long workerId) {
		return ResponseEntity.ok(this.payrollService.paySalary(workerId));
	}
	
	@GetMapping("/payrolls")
	@JsonView(PayrollViews.PayrollData.class)
	public ResponseEntity<List<Payroll>> getPayrolls() {
		return ResponseEntity.ok(this.payrollService.getPayrolls());
	}
	
}