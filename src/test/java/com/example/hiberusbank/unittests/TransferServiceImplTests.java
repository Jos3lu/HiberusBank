package com.example.hiberusbank.unittests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.server.ResponseStatusException;

import com.example.hiberusbank.models.Transfer;
import com.example.hiberusbank.models.Worker;
import com.example.hiberusbank.models.repositories.TransferRepository;
import com.example.hiberusbank.services.WorkerService;
import com.example.hiberusbank.servicesimpl.TransferServiceImpl;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
public class TransferServiceImplTests {

	@Mock
	private TransferRepository transferRepository;
	
	@Mock
	private WorkerService workerService;
	
	@InjectMocks
	private TransferServiceImpl transferService;
		
	@Test
	public void sendTransfer() {
		Worker sender = new Worker("Mike", "Smith", 1500D, 2000D);
		sender.setId(1L);
		Worker receiver = new Worker("John", "Brown", 1750D, 0D);
		receiver.setId(2L);
		Transfer transfer = new Transfer(sender, receiver, 500D);
		
		when(workerService.getWorker(1L)).thenReturn(sender);
		when(workerService.getWorker(2L)).thenReturn(receiver);
		
		Transfer resulTransfer = transferService.sendTransfer(1L, 2L, 500D);
		
		assertThat(transfer.getSender()).isEqualTo(resulTransfer.getSender());
		assertThat(transfer.getReceiver()).isEqualTo(resulTransfer.getReceiver());
		assertThat(transfer.getAmount()).isEqualTo(transfer.getAmount());
		verify(workerService, times(2)).getWorker(anyLong());
	}
	
	@Test
	public void sendTransfer_exception_balance() {
		Worker sender = new Worker("Mike", "Smith", 1500D, 0D);
		sender.setId(1L);
		Worker receiver = new Worker("John", "Brown", 1750D, 0D);
		receiver.setId(2L);
		
		when(workerService.getWorker(1L)).thenReturn(sender);
		when(workerService.getWorker(2L)).thenReturn(receiver);
		
		assertThrows(ResponseStatusException.class, 
				() -> transferService.sendTransfer(1L, 2L, 500D));
		verify(workerService, times(2)).getWorker(anyLong());
	}
	
	@Test 
	public void sendTransfer_exception_Antonio() {
		Worker sender = new Worker("Mike", "Smith", 1500D, 1000D);
		sender.setId(1L);
		Worker receiver = new Worker("Antonio", "García", 1750D, 0D);
		receiver.setId(2L);
		
		when(workerService.getWorker(1L)).thenReturn(sender);
		when(workerService.getWorker(2L)).thenReturn(receiver);
		
		assertThrows(ResponseStatusException.class, 
				() -> transferService.sendTransfer(1L, 2L, 500D));
		verify(workerService, times(2)).getWorker(anyLong());	
	}
	
	@Test
	public void sendTransfer_exception_not_multiple_10() {
		Worker sender = new Worker("Mike", "Smith", 1500D, 750D);
		sender.setId(1L);
		Worker receiver = new Worker("John", "Brown", 1750D, 0D);
		receiver.setId(2L);
		
		when(workerService.getWorker(1L)).thenReturn(sender);
		when(workerService.getWorker(2L)).thenReturn(receiver);
		
		assertThrows(ResponseStatusException.class, 
				() -> transferService.sendTransfer(1L, 2L, 255D));
		verify(workerService, times(2)).getWorker(anyLong());
	}
	
	@Test
	public void getTransfers_successful() {
		Worker sender = new Worker("Mike", "Smith", 1500D, 10000D);
		sender.setId(1L);
		Worker receiver = new Worker("John", "Brown", 1750D, 0D);
		receiver.setId(2L);
		Transfer transfer1 = new Transfer(sender, receiver, 500D);
		Transfer transfer2 = new Transfer(sender, receiver, 250D);
		Transfer transfer3 = new Transfer(sender, receiver, 100D);
		List<Transfer> transfers = new ArrayList<>(
				Arrays.asList(transfer1, transfer2, transfer3));
		
		when(transferRepository.findByFailed(false)).thenReturn(transfers);
		
		List<Transfer> resultTransfers = transferService.getTransfers(false);
		assertThat(transfers).isEqualTo(resultTransfers);
		verify(transferRepository).findByFailed(false);
	}
	
	@Test
	public void getTransfers_successful_notFound() {
		// No transfers found
		when(transferRepository.findByFailed(false)).thenReturn(new ArrayList<>());
		
		List<Transfer> resultTransfers = transferRepository.findByFailed(false);
		assertThat(resultTransfers).isEmpty();
		verify(transferRepository).findByFailed(false);
	}
	
	@Test
	public void getTransfers_failed() {
		Worker sender = new Worker("Mike", "Smith", 1500D, 0D);
		sender.setId(1L);
		Worker receiver = new Worker("Antonio", "Campanario", 1750D, 0D);
		receiver.setId(2L);
		Transfer transfer1 = new Transfer(sender, receiver, 100D);
		Transfer transfer2 = new Transfer(sender, receiver, 750D);
		Transfer transfer3 = new Transfer(sender, receiver, 300D);
		List<Transfer> transfers = new ArrayList<>(
				Arrays.asList(transfer1, transfer2, transfer3));
		
		when(transferRepository.findByFailed(true)).thenReturn(transfers);
		
		List<Transfer> resultTransfers = transferService.getTransfers(true);
		assertThat(transfers).isEqualTo(resultTransfers);
		verify(transferRepository).findByFailed(true);
	}
	
	@Test
	public void getTransfers_failed_notFound() {
		// No transfers found
		when(transferRepository.findByFailed(true)).thenReturn(new ArrayList<>());
		
		List<Transfer> resultTransfers = transferRepository.findByFailed(true);
		assertThat(resultTransfers).isEmpty();
		verify(transferRepository).findByFailed(true);	
	}
	
}
