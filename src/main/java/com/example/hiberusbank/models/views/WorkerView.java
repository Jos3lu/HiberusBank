package com.example.hiberusbank.models.views;

public class WorkerView {

	private WorkerView() {}
	
	public static interface BasicData {}
	
	public static interface PayrollData extends PayrollView.BasicData {}
	
	public static interface TransferData extends TransferView.BasicData {}
	
	public static interface WorkerData extends BasicData, PayrollData, TransferData {}
	
}
