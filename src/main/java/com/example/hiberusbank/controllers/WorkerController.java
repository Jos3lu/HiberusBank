package com.example.hiberusbank.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hiberusbank.models.Worker;
import com.example.hiberusbank.models.dtos.WorkerDto;
import com.example.hiberusbank.models.views.WorkerViews;
import com.example.hiberusbank.services.WorkerService;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class WorkerController {

	private final WorkerService workerService;
	
	public WorkerController(WorkerService workerService) {
		this.workerService = workerService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<Void> registerWorker(@Valid @RequestBody WorkerDto workerDto) {
		Worker worker = new Worker(workerDto.getName(), workerDto.getLastName(), 
				workerDto.getGrossSalary(), 0.0);
		this.workerService.registerWorker(worker);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping("/workers/{workerId}")
	@JsonView(WorkerViews.WorkerData.class)
	public ResponseEntity<Worker> getWorker(@PathVariable Long workerId) {
		return ResponseEntity.ok(this.workerService.getWorker(workerId));
	}
	
	@PutMapping("/workers/{workerId}/raise-salary")
	@JsonView(WorkerViews.ExtendedData.class)
	public ResponseEntity<Worker> raiseSalary(@PathVariable Long workerId, @RequestParam Double amount) {
		return ResponseEntity.ok(this.workerService.raiseSalary(workerId, amount));
	}
	
	@DeleteMapping("/workers/{workerId}")
	public ResponseEntity<Void> deleteWorker(@PathVariable Long workerId) {
		this.workerService.deleteWorker(workerId);
		return ResponseEntity.noContent().build();
	}
	
}
