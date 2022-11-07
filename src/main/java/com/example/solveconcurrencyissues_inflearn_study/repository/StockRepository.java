package com.example.solveconcurrencyissues_inflearn_study.repository;

import com.example.solveconcurrencyissues_inflearn_study.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
