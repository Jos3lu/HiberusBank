package com.example.hiberusbank.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hiberusbank.models.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, Long> {

}
