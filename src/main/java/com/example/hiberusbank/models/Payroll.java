package com.example.hiberusbank.models;

import java.time.LocalDateTime;

import com.example.hiberusbank.models.views.PayrollView;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
public class Payroll {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(PayrollView.BasicData.class)
	private Long id;
	
	@NotNull(message = "Payment date cannot be empty")
	@JsonView(PayrollView.BasicData.class)
	private LocalDateTime paymentDate;
	
	@NotNull(message = "Net amount cannot be empty")
	@Positive(message = "Net amount must be greater or equal to 0")
	@JsonView(PayrollView.BasicData.class)
	private Double netAmount;
	
	public Payroll() {}

	public Payroll(@NotNull(message = "Payment date cannot be empty") LocalDateTime paymentDate,
			@NotNull(message = "Net amount cannot be empty") @Positive(message = "Net amount must be greater or equal to 0") Double netAmount) {
		this.paymentDate = paymentDate;
		this.netAmount = netAmount;
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
	
}
