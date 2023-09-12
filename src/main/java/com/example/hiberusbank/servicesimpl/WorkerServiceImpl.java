package com.example.hiberusbank.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.hiberusbank.models.Worker;
import com.example.hiberusbank.models.repositories.WorkerRepository;
import com.example.hiberusbank.services.WorkerService;

@Service
public class WorkerServiceImpl implements WorkerService {
	
	@Autowired
	private WorkerRepository workerRepository;
	
	@Override
	public Worker registerWorker(Worker worker) {
		// Users can have the same name and last name
		return this.workerRepository.save(worker);
	}
	
	@Override
	public Worker getWorker(Long workerId) {
		return this.workerRepository.findById(workerId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Worker not found"));
	}

	@Override
	public Worker raiseSalary(Long workerId, Double amount) {
		Worker worker = this.getWorker(workerId);
		worker.setGrossSalary(worker.getGrossSalary() + amount);
		return this.workerRepository.save(worker);
	}
	
	@Override
	public void deleteWorker(Long workerId) {
		Worker worker = this.getWorker(workerId);
		this.workerRepository.delete(worker);
	}

	@Override
	public Worker filterFailedTransfers(Worker worker) {
		worker.getTransfersSent().removeIf(e -> e.getFailed());
		worker.getTransfersReceived().removeIf(e -> e.getFailed());
		return worker;
	}

}
