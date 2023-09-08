package com.example.hiberusbank.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hiberusbank.models.Transfer;
import com.example.hiberusbank.models.views.TransferViews;
import com.example.hiberusbank.services.TransferService;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/api")
public class TransferController {

	private final TransferService transferService;
	
	public TransferController(TransferService transferService) {
		this.transferService = transferService;
	}
	
	@PostMapping("/workers/sender/{senderId}/receiver/{receiverId}/transfer")
	@JsonView(TransferViews.TransferData.class)
	public ResponseEntity<Transfer> sendTransfer(@PathVariable Long senderId, @PathVariable Long receiverId,
			@RequestParam Double amount) {
		return ResponseEntity.ok(this.transferService.sendTransfer(senderId, receiverId, amount));
	}
	
	@GetMapping("/transfers")
	@JsonView(TransferViews.CompleteTransferData.class)
	public ResponseEntity<List<Transfer>> getTransfers() {
		return ResponseEntity.ok(this.transferService.getTransfers(false));
	}
	
	@GetMapping("/failed-transfers")
	@JsonView(TransferViews.CompleteTransferData.class)
	public ResponseEntity<List<Transfer>> getFailedTransfers() {
		return ResponseEntity.ok(this.transferService.getTransfers(true));
	}
	
}
