package com.stock.screener.domain.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

import static jakarta.persistence.FetchType.LAZY;
import static java.math.RoundingMode.HALF_UP;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "analyst_recommendation")
@EntityListeners(AuditingEntityListener.class)
public class AnalystRecommendation {

    @EmbeddedId
    private CompoundId id;

    private Integer strongBuy;
    private Integer buy;
    private Integer hold;
    private Integer sell;
    private Integer strongSell;

    @MapsId("symbol")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "symbol", nullable = false)
    private Stock stock;

    public Integer total() {
        return (strongBuy != null ? strongBuy : 0) +
                (buy != null ? buy : 0) +
                (hold != null ? hold : 0) +
                (sell != null ? sell : 0) +
                (strongSell != null ? strongSell : 0);
    }

    public BigDecimal bullishRatio() {
        int total = total();
        if (total == 0) return BigDecimal.ZERO;

        int bullish = (strongBuy != null ? strongBuy : 0) + (buy != null ? buy : 0);
        return new BigDecimal(bullish).divide(
                new BigDecimal(total), 4, HALF_UP).stripTrailingZeros();
    }

    public AnalystRecommendation fromStock(String seekingAlphaTrackerId) {
        return AnalystRecommendation.builder()
                .id(CompoundId.builder()
                        .symbol(stock.getSymbol())
                        .build())
                .build();
    }
}
