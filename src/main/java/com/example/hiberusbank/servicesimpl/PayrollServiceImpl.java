package com.example.hiberusbank.servicesimpl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.hiberusbank.models.Payroll;
import com.example.hiberusbank.models.Worker;
import com.example.hiberusbank.models.repositories.WorkerRepository;
import com.example.hiberusbank.services.PayrollService;
import com.example.hiberusbank.services.WorkerService;

@Service
public class PayrollServiceImpl implements PayrollService {
	
	private final WorkerRepository workerRepository;
	private final WorkerService workerService;
	
	public PayrollServiceImpl(WorkerRepository workerRepository, WorkerService workerService) {
		this.workerRepository = workerRepository;
		this.workerService = workerService;
	}

	@Override
	public Payroll paySalary(Long workerId) {
		Worker worker = this.workerService.getWorker(workerId);
		double netSalary = calculateNetSalary(worker.getGrossSalary());
		worker.setBalance(worker.getBalance() + netSalary);
		
		Payroll payroll = new Payroll(LocalDateTime.now(), netSalary);
		worker.getPayrolls().add(payroll);
		workerRepository.save(worker);
		return payroll;
	}
	
	private Double calculateNetSalary(Double grossSalary) {
		return grossSalary * (1 - 0.0525); // 5.25% IRPF
	}

}
