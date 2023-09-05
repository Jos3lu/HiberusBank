package com.example.demo.models.views;

public class TransferView {

	private TransferView() {}
	
	public static interface BasicData {}
	
	public static interface WorkerData extends WorkerView.BasicData {}
	
	public static interface TransferData extends BasicData, WorkerData {}
	
	
}
