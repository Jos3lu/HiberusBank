package com.example.hiberusbank.services;

import java.util.List;

import com.example.hiberusbank.models.Transfer;

public interface TransferService {

	/**
	 * Make transfer between workers
	 * 
	 * @param senderId Sender worker ID
	 * @param receiverId Receiver worker ID
	 * @param amount Transfer amount
	 * @return Transfer
	 */
	Transfer sendTransfer(Long senderId, Long receiverId, Double amount);

	/**
	 * Get transfers of all the workers
	 * 
	 * @param failed If true -> failed transfers, otherwise -> successful transfers
	 * @return List<Transfer>
	 */
	List<Transfer> getTransfers(Boolean failed);

}
