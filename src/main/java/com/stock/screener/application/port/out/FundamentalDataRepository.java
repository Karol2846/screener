package com.stock.screener.application.port.out;

import com.stock.screener.application.domain.model.CompoundId;
import com.stock.screener.application.domain.model.FundamentalData;
import com.stock.screener.application.domain.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundamentalDataRepository extends JpaRepository<FundamentalData, CompoundId> {

}
