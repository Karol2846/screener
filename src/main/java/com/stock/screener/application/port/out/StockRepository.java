package com.stock.screener.application.port.out;

import com.stock.screener.domain.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StockRepository extends JpaRepository<Stock, String> {

    List<Stock> findAllBySymbolIn(List<String> symbols);

    List<String> findAllSymbols();

    boolean existsBySymbol(String symbol);

}
