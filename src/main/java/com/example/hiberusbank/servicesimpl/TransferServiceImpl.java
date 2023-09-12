package com.example.hiberusbank.servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.hiberusbank.models.Transfer;
import com.example.hiberusbank.models.Worker;
import com.example.hiberusbank.models.repositories.TransferRepository;
import com.example.hiberusbank.services.TransferService;
import com.example.hiberusbank.services.WorkerService;

@Service
public class TransferServiceImpl implements TransferService {

	private static final String FORBIDDEN_USER = "Antonio";
	
	@Autowired
	private TransferRepository transferRepository;
	
	@Autowired
	private WorkerService workerService;

	@Override
	public Transfer sendTransfer(Long senderId, Long receiverId, Double amount) {
		Worker sender = this.workerService.getWorker(senderId);
		Worker receiver = this.workerService.getWorker(receiverId);
		
		Transfer transfer = new Transfer(sender, receiver, amount);
		boolean isValidTransfer = this.isValidTransfer(sender, receiver, amount);
		if (isValidTransfer) {
			// Successful transfer
			transfer.setFailed(false);
			
			// Decrease balance sender
			sender.setBalance(sender.getBalance() - amount);
			
			// Increase balance receiver
			receiver.setBalance(receiver.getBalance() + amount);
			this.workerService.registerWorker(receiver);
		} else {
			// Transfer failed
			transfer.setFailed(true);
		}
		
		// Add transfer to sender whether or not it failed
		sender.getTransfersSent().add(transfer);
		this.workerService.registerWorker(sender);
		
		if (!isValidTransfer) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transaction failed");
		}
		
		return transfer;
	}
	
	private Boolean isValidTransfer(Worker sender, Worker receiver, Double amount) {
		return !(isAntonio(sender.getName())) && !(isAntonio(receiver.getName()))
				&& (amount % 10 == 0) && sender.getBalance() >= amount;
	}
	
	private Boolean isAntonio(String name) {
		return name.equalsIgnoreCase(FORBIDDEN_USER);
	}

	@Override
	public List<Transfer> getTransfers(Boolean failed) {
		return this.transferRepository.findByFailed(failed);
	}	
}
