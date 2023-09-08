package com.example.hiberusbank.models;

import java.time.LocalDateTime;

import com.example.hiberusbank.models.views.PayrollViews;
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
public class Payroll {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(PayrollViews.ExtendedData.class)
	private Long id;
	
	@NotNull(message = "Payment date cannot be empty")
	@JsonView(PayrollViews.BasicData.class)
	private LocalDateTime paymentDate;
	
	@NotNull(message = "Net amount cannot be empty")
	@Positive(message = "Net amount must be greater or equal to 0")
	@JsonView(PayrollViews.BasicData.class)
	private Double netAmount;
	
	@ManyToOne
	@JoinColumn(name = "worker_id")
	@NotNull(message = "Worker cannot be empty")
	@JsonView(PayrollViews.WorkerData.class)
	private Worker worker;
	
	public Payroll() {}

	public Payroll(@NotNull(message = "Payment date cannot be empty") LocalDateTime paymentDate,
			@NotNull(message = "Net amount cannot be empty") @Positive(message = "Net amount must be greater or equal to 0") Double netAmount,
			@NotNull(message = "Worker cannot be empty") Worker worker) {
		super();
		this.paymentDate = paymentDate;
		this.netAmount = netAmount;
		this.worker = worker;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Double getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(Double netAmount) {
		this.netAmount = netAmount;
	}
	
	public Worker getWorker() {
		return worker;
	}
	
	public void setWorker(Worker worker) {
		this.worker = worker;
	}
	
}
