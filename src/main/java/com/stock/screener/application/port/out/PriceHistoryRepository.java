package com.stock.screener.application.port.out;

import com.stock.screener.domain.model.CompoundId;
import com.stock.screener.domain.model.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory, CompoundId> {

}
