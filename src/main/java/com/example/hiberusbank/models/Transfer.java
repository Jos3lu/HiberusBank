package com.example.hiberusbank.models;

import com.example.hiberusbank.models.views.TransferViews;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
public class Transfer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(TransferViews.BasicData.class)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "worker_id")
	@NotNull(message = "Sender cannot be empty")
	@JsonView(TransferViews.WorkerData.class)
	private Worker sender;
	
	@ManyToOne
	@JoinColumn(name = "worker_id")
	@NotNull(message = "Receiver cannot be empty")
	@JsonView(TransferViews.WorkerData.class)
	private Worker receiver;
	
	@NotNull(message = "Amount cannot be empty")
	@Positive(message = "The amount must be greater than 0")
	@JsonView(TransferViews.BasicData.class)
	private double amount;
	
	public Transfer() {}
		
	public Transfer(@NotNull(message = "Sender cannot be empty") Worker sender,
			@NotNull(message = "Receiver cannot be empty") Worker receiver,
			@NotNull(message = "Amount cannot be empty") @Positive(message = "The amount must be greater than 0") double amount) {
		this.sender = sender;
		this.receiver = receiver;
		this.amount = amount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Worker getSender() {
		return sender;
	}

	public void setSender(Worker sender) {
		this.sender = sender;
	}

	public Worker getReceiver() {
		return receiver;
	}

	public void setReceiver(Worker receiver) {
		this.receiver = receiver;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}
