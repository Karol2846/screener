package com.stock.screener.application.port.out;

import com.stock.screener.application.domain.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, String> {

}
