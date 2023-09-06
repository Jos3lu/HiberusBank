package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

}
