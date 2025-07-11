package com.stock.screener.application.port.out;

import com.stock.screener.application.domain.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, String> {

    List<Stock> findAllBySymbolIn(List<String> symbols);

    Optional<Stock> findBySymbol(String symbol);
    boolean existsBySymbol(String symbol);

}
