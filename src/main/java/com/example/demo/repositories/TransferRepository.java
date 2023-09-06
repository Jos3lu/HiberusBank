package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, Long> {

}
