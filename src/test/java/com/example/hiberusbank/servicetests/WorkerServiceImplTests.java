package com.example.hiberusbank.servicetests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.server.ResponseStatusException;

import com.example.hiberusbank.models.Worker;
import com.example.hiberusbank.models.repositories.WorkerRepository;
import com.example.hiberusbank.servicesimpl.WorkerServiceImpl;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
public class WorkerServiceImplTests {
	
	@Mock
	private WorkerRepository workerRepository;
	
	@InjectMocks
	private WorkerServiceImpl workerService;
	
	private Worker worker;
	
	@BeforeEach
	public void setUp() {
		worker = new Worker("Mike", "Smith", 1500D, 0D);
		worker.setId(1L);
	}

	@Test
	public void registerWorker() {
		when(workerRepository.save(any(Worker.class))).thenReturn(worker);
		
		assertDoesNotThrow(() -> workerService.registerWorker(worker));
		verify(workerRepository).save(any(Worker.class));
	}
	
	@Test
	public void getWorker() {
		when(workerRepository.findById(anyLong())).thenReturn(Optional.of(worker));
		
		Worker resultWorker = workerService.getWorker(1L);
		assertThat(worker).isEqualTo(resultWorker);
		verify(workerRepository).findById(anyLong());
	}
	
	@Test
	public void getWorker_exception() {
		// Worker not found
		Long workerId = 2L;
		when(workerRepository.findById(workerId)).thenReturn(Optional.empty());
		
		assertThrows(ResponseStatusException.class, () -> workerService.getWorker(2L));
		verify(workerRepository).findById(anyLong());
	}
	
	@Test
	public void raiseSalary() {
		Worker updatedWorker = new Worker("Mike", "Smith", 2500D, 0D);
		updatedWorker.setId(1L);
		
		when(workerRepository.findById(anyLong())).thenReturn(Optional.of(worker));
		when(workerRepository.save(any(Worker.class))).thenReturn(updatedWorker);
		
		Worker resultWorker = workerService.raiseSalary(1L, 1000D);
		assertThat(updatedWorker).isEqualTo(resultWorker);
		verify(workerRepository).findById(anyLong());
		verify(workerRepository).save(any(Worker.class));
	}
	
	@Test
	public void deleteWorker() {
		when(workerRepository.findById(anyLong()))
			.thenReturn(Optional.of(worker)).thenReturn(Optional.empty());
		
		workerService.deleteWorker(anyLong());
		
		assertThrows(ResponseStatusException.class, () -> workerService.getWorker(1L));
		verify(workerRepository, times(2)).findById(anyLong());
	}
	
	@Test
	public void deleteWorker_exception() {
		// User not found
		when(workerRepository.findById(anyLong())).thenReturn(Optional.empty());
		
		assertThrows(ResponseStatusException.class, () -> workerService.deleteWorker(1L));
		verify(workerRepository).findById(anyLong());
	}
	
}
