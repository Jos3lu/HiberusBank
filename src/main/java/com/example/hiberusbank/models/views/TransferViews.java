package com.example.hiberusbank.models.views;

public class TransferViews {

	private TransferViews() {}
	
	public static interface BasicData {}
	
	public static interface ExtendedData {}
	
	public static interface GeneralData extends BasicData, ExtendedData {}
	
	public static interface WorkerData extends WorkerViews.BasicData {}
	
	public static interface TransferData extends BasicData, WorkerData {}
	
	public static interface CompleteTransferData extends TransferData, ExtendedData {}
	
	
}
