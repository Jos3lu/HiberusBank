package com.example.hiberusbank.models.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hiberusbank.models.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, Long> {

	List<Transfer> findByFailed(Boolean failed);
	
}
