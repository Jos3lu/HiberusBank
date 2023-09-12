package com.example.hiberusbank.services;

import com.example.hiberusbank.models.Worker;

public interface WorkerService {

	/**
	 * Register new worker
	 * 
	 * @param worker Worker information
	 */
	public Worker registerWorker(Worker worker);

	/**
	 * Delete worker
	 * 
	 * @param workerId Worker ID
	 */
	public void deleteWorker(Long workerId);

	/**
	 * Get information of a worker
	 * 
	 * @param workerId Worker ID
	 * @return Worker
	 */
	public Worker getWorker(Long workerId);

	/**
	 * Raise salary of worker
	 * 
	 * @param workerId Worker ID
	 * @param amount Salary to increase
	 * @return Worker
	 */
	public Worker raiseSalary(Long workerId, Double amount);

	/**
	 * Filter failed transfers from worker
	 * 
	 * @param worker
	 * @return
	 */
	public Worker filterFailedTransfers(Worker worker);

}
