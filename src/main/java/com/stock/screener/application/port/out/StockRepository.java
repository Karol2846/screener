package com.stock.screener.application.port.out;

import com.stock.screener.domain.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface StockRepository extends JpaRepository<Stock, String> {

    @Query("SELECT s.symbol FROM Stock s")
    List<String> findAllSymbols();
}
