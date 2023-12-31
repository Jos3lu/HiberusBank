package com.example.hiberusbank.models.views;

public class WorkerViews {

	private WorkerViews() {}
	
	public static interface BasicData {}
	
	public static interface ExtendedData extends BasicData {}
	
	public static interface PayrollData extends PayrollViews.GeneralData {}
	
	public static interface TransferData extends TransferViews.GeneralData {}
	
	public static interface WorkerData extends ExtendedData, PayrollData, TransferData {}
	
}
