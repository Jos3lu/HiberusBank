package com.example.hiberusbank.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hiberusbank.models.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

}
