package com.example.hiberusbank.servicesimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.hiberusbank.models.Payroll;
import com.example.hiberusbank.models.Worker;
import com.example.hiberusbank.models.repositories.PayrollRepository;
import com.example.hiberusbank.services.PayrollService;
import com.example.hiberusbank.services.WorkerService;

@Service
public class PayrollServiceImpl implements PayrollService {

	private final PayrollRepository payrollRepository;
	private final WorkerService workerService;
	
	public PayrollServiceImpl(PayrollRepository payrollRepository, WorkerService workerService) {
		this.payrollRepository = payrollRepository;
		this.workerService = workerService;
	}

	@Override
	public Payroll paySalary(Long workerId) {
		Worker worker = this.workerService.getWorker(workerId);
		double netSalary = calculateNetSalary(worker.getGrossSalary());
		worker.setBalance(worker.getBalance() + netSalary);
		
		Payroll payroll = new Payroll(LocalDateTime.now(), netSalary, worker);
		worker.getPayrolls().add(payroll);
		this.workerService.registerWorker(worker);
		return payroll;
	}
	
	private Double calculateNetSalary(Double grossSalary) {
		return grossSalary * (1 - 0.0525); // 5.25% IRPF
	}

	@Override
	public List<Payroll> getPayrolls() {
		return this.payrollRepository.findAll();
	}

}
