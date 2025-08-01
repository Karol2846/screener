package com.stock.screener.application.port.out;

import com.stock.screener.domain.model.CompoundId;
import com.stock.screener.domain.model.FundamentalData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundamentalDataRepository extends JpaRepository<FundamentalData, CompoundId> {

}
