package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
public class Worker {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank(message = "Name cannot be empty")
	@Size(max = 50, message = "Name must be less than 50 characters")
	private String name;
	
	@NotBlank(message = "Last name cannot be empty")
	@Size(max = 50, message = "Last name must be less than 50 characters")
	private String lastName;
	
	@NotNull(message = "Gross salary cannot be empty")
	@Positive(message = "Gross salary must be greater than 0")
	private Double grossSalary;
	
	@NotNull(message = "Balance cannot be empty")
	@PositiveOrZero(message = "Balance must be greater or equal to 0")
	private Double balance;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "worker_id")
	private List<Payroll> payrolls = new ArrayList<>();
	
	@OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Transfer> transfersSent = new ArrayList<>();
	
	@OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Transfer> transfersReceived = new ArrayList<>();
	
	public Worker() {}

	public Worker(
			@NotBlank(message = "Name cannot be empty") @Size(max = 50, message = "Name must be less than 50 characters") String name,
			@NotBlank(message = "Last name cannot be empty") @Size(max = 50, message = "Last name must be less than 50 characters") String lastName,
			@NotNull(message = "Gross salary cannot be empty") @Positive(message = "Gross salary must be greater than 0") Double grossSalary,
			@NotNull(message = "Balance cannot be empty") @PositiveOrZero(message = "Balance must be greater or equal to 0") Double balance) {
		this.name = name;
		this.lastName = lastName;
		this.grossSalary = grossSalary;
		this.balance = balance;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Double getGrossSalary() {
		return grossSalary;
	}

	public void setGrossSalary(Double grossSalary) {
		this.grossSalary = grossSalary;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public List<Payroll> getPayrolls() {
		return payrolls;
	}

	public void setPayrolls(List<Payroll> payrolls) {
		this.payrolls = payrolls;
	}

	public List<Transfer> getTransfersSent() {
		return transfersSent;
	}

	public void setTransfersSent(List<Transfer> transfersSent) {
		this.transfersSent = transfersSent;
	}

	public List<Transfer> getTransfersReceived() {
		return transfersReceived;
	}

	public void setTransfersReceived(List<Transfer> transfersReceived) {
		this.transfersReceived = transfersReceived;
	}
	
}
