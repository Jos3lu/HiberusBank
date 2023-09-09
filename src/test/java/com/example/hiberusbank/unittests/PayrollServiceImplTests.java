package com.example.hiberusbank.unittests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import com.example.hiberusbank.models.Payroll;
import com.example.hiberusbank.models.Worker;
import com.example.hiberusbank.models.repositories.PayrollRepository;
import com.example.hiberusbank.services.WorkerService;
import com.example.hiberusbank.servicesimpl.PayrollServiceImpl;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
public class PayrollServiceImplTests {

	@Mock
	private PayrollRepository payrollRepository;
	
	@Mock
	private WorkerService workerService;
	
	@InjectMocks
	private PayrollServiceImpl payrollService;
	
	private Worker worker;
	
	@BeforeEach
	public void setUp() {
		worker = new Worker("Mike", "Smith", 1500D, 0D);
		worker.setId(1L);
	}
		
	@Test
	public void paySalary() {
		Payroll payroll = new Payroll(null, 1500D * (1 - 0.0525), worker);
		when(workerService.getWorker(anyLong())).thenReturn(worker);
		
		Payroll resultPayroll = payrollService.paySalary(1L);
		
		assertThat(payroll.getNetAmount()).isEqualTo(resultPayroll.getNetAmount());
		assertThat(payroll.getWorker()).isEqualTo(resultPayroll.getWorker());
		verify(workerService).getWorker(anyLong());
	}
	
	@Test
	public void getPayrolls() {
		Payroll payroll1 = new Payroll(null, 1500D, worker);
		payroll1.setId(1L);
		Payroll payroll2 = new Payroll(null, 2000D, worker);
		payroll2.setId(2L);
		Payroll payroll3 = new Payroll(null, 3000D, worker);
		payroll3.setId(3L);
		List<Payroll> payrolls = new ArrayList<>(Arrays.asList(payroll1, payroll2, payroll3));
		
		when(payrollRepository.findAll()).thenReturn(payrolls);
		
		List<Payroll> resultPayrolls = payrollService.getPayrolls();
		assertThat(payrolls).isEqualTo(resultPayrolls);
		verify(payrollRepository).findAll();
	}
	
	@Test
	public void getPayrolls_notFound() {
		// No payrolls found
		when(payrollRepository.findAll()).thenReturn(new ArrayList<>());
		
		List<Payroll> resultPayrolls = payrollRepository.findAll();
		assertThat(resultPayrolls).isEmpty();
		verify(payrollRepository).findAll();
	}
	
}
