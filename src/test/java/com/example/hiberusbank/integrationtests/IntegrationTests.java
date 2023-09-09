package com.example.hiberusbank.integrationtests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.hiberusbank.models.Payroll;
import com.example.hiberusbank.models.Transfer;
import com.example.hiberusbank.models.Worker;
import com.example.hiberusbank.models.dtos.WorkerDto;
import com.example.hiberusbank.models.views.TransferViews;
import com.example.hiberusbank.models.views.WorkerViews;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureMockMvc
public class IntegrationTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private MvcResult mvcResult;
		
	@Test
	@Order(1)
	public void registerWorker_raiseSalary_getWorker() throws Exception {
		// Register worker
		WorkerDto workerDto = new WorkerDto();
		workerDto.setName("John");
		workerDto.setLastName("Smith");
		workerDto.setGrossSalary(1500D);
		
		Worker expectedWorker = new Worker("John", "Smith", 1500D, 0D);
		expectedWorker.setId(1L);
		
		mvcResult = mockMvc.perform(post("/api/register").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(workerDto))).andExpect(status().isCreated()).andReturn();
		
		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		String expectedResponseBody = objectMapper.writerWithView(WorkerViews.ExtendedData.class)
				.writeValueAsString(expectedWorker);
		
		assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
		
		// Raise salary worker
		expectedWorker.setGrossSalary(expectedWorker.getGrossSalary() + 500D);
		mvcResult = mockMvc.perform(put("/api/workers/1/raise-salary").param("amount", "500"))
				.andExpect(status().isOk()).andReturn();
		
		actualResponseBody = mvcResult.getResponse().getContentAsString();
		expectedResponseBody = objectMapper.writerWithView(WorkerViews.ExtendedData.class)
				.writeValueAsString(expectedWorker);
		
		assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
		
		// Get worker
		mvcResult = mockMvc.perform(get("/api/workers/1")).andExpect(status().isOk()).andReturn();
		
		actualResponseBody = mvcResult.getResponse().getContentAsString();
		expectedResponseBody = objectMapper.writerWithView(WorkerViews.WorkerData.class)
				.writeValueAsString(expectedWorker);
		
		assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
	}
	
	@Test
	@Order(2)
	public void registerWorker_paySalary_getPayrolls() throws Exception {
		// Register worker
		WorkerDto workerDto = new WorkerDto();
		workerDto.setName("Robert");
		workerDto.setLastName("Hall");
		workerDto.setGrossSalary(1750D);
		
		Worker expectedWorker = new Worker("Robert", "Hall", 1750D, 0D);
		expectedWorker.setId(2L);
		
		mockMvc.perform(post("/api/register").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(workerDto))).andExpect(status().isCreated()).andReturn();
		
		// Pay salary to worker
		Payroll expectedPayroll = new Payroll(null, expectedWorker.getGrossSalary() * (1 - 0.0525), expectedWorker);
		expectedPayroll.setId(1L);
		
		mvcResult = mockMvc.perform(post("/api/workers/2/payroll").header("Authorization", "Robert"))
				.andExpect(status().isOk()).andReturn();
		
		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		Payroll actualPayroll = objectMapper.readValue(actualResponseBody, Payroll.class);
		
		assertThat(actualPayroll.getNetAmount()).isEqualTo(expectedPayroll.getNetAmount());
		
		// Get payrolls
		mockMvc.perform(get("/api/payrolls")).andExpect(status().isOk());
	}
	
	@Test
	@Order(3)
	public void makeFailedAndSuccessfulTransfers_getFailedAndSuccessfulTransfers_deleteWorker() 
			throws Exception {
		Worker expectedSender = new Worker("Robert", "Hall", 1750D, 0D);
		expectedSender.setId(2L);
		
		Worker expectedReceiver = new Worker("John", "Smith", 1500D, 0D);
		expectedReceiver.setId(1L);
		
		// Make transfer
		Transfer expectedTransfer = new Transfer(expectedSender, expectedReceiver, 250D);
		expectedTransfer.setId(2L);
		
		// Failed transfer
		mockMvc.perform(post("/api/workers/sender/1/receiver/2/transfer").param("amount", "250"))
				.andExpect(status().isBadRequest());
		
		// Successful transfer
		mvcResult = mockMvc.perform(post("/api/workers/sender/2/receiver/1/transfer").param("amount", "250"))
				.andExpect(status().isOk()).andReturn();
		
		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		String expectedResponseBody = objectMapper.writerWithView(TransferViews.TransferData.class)
				.writeValueAsString(expectedTransfer);
		
		assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
		
		// Get successful transfers
		List<Transfer> expectedTransfers = new ArrayList<>(Arrays.asList(expectedTransfer));
		
		mvcResult = mockMvc.perform(get("/api/transfers")).andExpect(status().isOk())
				.andReturn();
		
		actualResponseBody = mvcResult.getResponse().getContentAsString();
		expectedResponseBody = objectMapper.writerWithView(TransferViews.CompleteTransferData.class)
				.writeValueAsString(expectedTransfers);
		
		assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
		
		// Get failed transfers
		expectedTransfer = new Transfer(expectedReceiver, expectedSender, 250D);
		expectedTransfer.setId(1L);
		expectedTransfers = new ArrayList<>(Arrays.asList(expectedTransfer));
		
		mvcResult = mockMvc.perform(get("/api/failed-transfers")).andExpect(status().isOk())
				.andReturn();
		
		actualResponseBody = mvcResult.getResponse().getContentAsString();
		expectedResponseBody = objectMapper.writerWithView(TransferViews.CompleteTransferData.class)
				.writeValueAsString(expectedTransfers);
		
		assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);
		
		// Delete worker
		mockMvc.perform(delete("/api/workers/1")).andExpect(status().isNoContent());
		mockMvc.perform(delete("/api/workers/2")).andExpect(status().isNoContent());
	}
	
}
