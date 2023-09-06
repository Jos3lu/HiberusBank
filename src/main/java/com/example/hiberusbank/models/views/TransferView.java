package com.example.hiberusbank.models.views;

public class TransferView {

	private TransferView() {}
	
	public static interface BasicData {}
	
	public static interface WorkerData extends WorkerView.BasicData {}
	
	public static interface TransferData extends BasicData, WorkerData {}
	
	
}
