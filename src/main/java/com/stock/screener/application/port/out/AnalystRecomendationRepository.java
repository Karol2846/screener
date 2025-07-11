package com.stock.screener.application.port.out;

import com.stock.screener.application.domain.model.AnalystRecommendation;
import com.stock.screener.application.domain.model.CompoundId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalystRecomendationRepository extends JpaRepository<AnalystRecommendation, CompoundId> {

}
