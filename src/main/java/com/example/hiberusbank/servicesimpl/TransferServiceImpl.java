package com.example.hiberusbank.servicesimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.hiberusbank.models.Transfer;
import com.example.hiberusbank.models.Worker;
import com.example.hiberusbank.models.repositories.TransferRepository;
import com.example.hiberusbank.services.TransferService;
import com.example.hiberusbank.services.WorkerService;

@Service
public class TransferServiceImpl implements TransferService {

	private static final String FORBIDDEN_USER = "Antonio";
	
	private final TransferRepository transferRepository;
	private final WorkerService workerService;
	
	public TransferServiceImpl(TransferRepository transferRepository, WorkerService workerService) {
		this.transferRepository = transferRepository;
		this.workerService = workerService;
	}

	@Override
	public Transfer sendTransfer(Long senderId, Long receiverId, Double amount) {
		Worker sender = this.workerService.getWorker(senderId);
		Worker receiver = this.workerService.getWorker(receiverId);
		
		Transfer transfer = new Transfer(sender, receiver, amount);
		if (this.isValidTransfer(sender, receiver, amount)) {
			// Successful transfer
			transfer.setFailed(false);
			
			// Decrease balance sender & add transfer
			sender.setBalance(sender.getBalance() - amount);
			sender.getTransfersSent().add(transfer);
			
			// Increase balance receiver & add transfer
			receiver.setBalance(receiver.getBalance() + amount);
			receiver.getTransfersReceived().add(transfer);
			this.workerService.registerWorker(receiver);
		} else {
			// Transfer failed
			transfer.setFailed(true);
			sender.getTransfersFailed().add(transfer);
		}
		this.workerService.registerWorker(sender);
		
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
