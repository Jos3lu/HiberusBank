package com.example.hiberusbank.servicesimpl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.hiberusbank.models.Worker;
import com.example.hiberusbank.models.repositories.WorkerRepository;
import com.example.hiberusbank.services.WorkerService;

@Service
public class WorkerServiceImpl implements WorkerService {
	
	private final WorkerRepository workerRepository;
	
	public WorkerServiceImpl(WorkerRepository workerRepository) {
		this.workerRepository = workerRepository;
	}

	@Override
	public void registerWorker(Worker worker) {
		// Users can have the same name and last name
		this.workerRepository.save(worker);
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
		return workerRepository.save(worker);
	}
	
	@Override
	public void deleteWorker(Long workerId) {
		Worker worker = this.getWorker(workerId);
		this.workerRepository.delete(worker);
	}

}
