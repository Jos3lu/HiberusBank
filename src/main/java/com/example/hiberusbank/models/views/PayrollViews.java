package com.example.hiberusbank.models.views;

public class PayrollViews {

	private PayrollViews() {}
	
	public static interface BasicData {}
	
	public static interface ExtendedData {}
	
	public static interface GeneralData extends BasicData, ExtendedData {}
	
	public static interface WorkerData extends WorkerViews.BasicData {}
	
	public static interface PayrollData extends GeneralData, WorkerData {}
	
}
