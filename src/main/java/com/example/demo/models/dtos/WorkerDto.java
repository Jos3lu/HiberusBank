package com.example.demo.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class WorkerDto {

	@NotBlank(message = "Name cannot be empty")
	@Size(max = 50, message = "Name must be less than 50 characters")
	private String name;
	
	@NotBlank(message = "Last name cannot be empty")
	@Size(max = 50, message = "Last name must be less than 50 characters")
	private String lastName;
	
	@NotNull(message = "Gross salary cannot be empty")
	private Double grossSalary;
	
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
	
}
