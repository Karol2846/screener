package com.stock.screener.application.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;
import static java.math.RoundingMode.HALF_UP;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true, chain = true)
@Table(name = "analyst_recommendation")
public class AnalystRecommendation {

    @Id
    private UUID id;

    @Column(insertable = false, updatable = false)
    private String symbol;

    private Integer strongBuy;
    private Integer buy;
    private Integer hold;
    private Integer sell;
    private Integer strongSell;

    @CreatedDate
    private LocalDate createdAt;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "symbol", referencedColumnName = "symbol")
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
        return new BigDecimal(bullish).divide(new BigDecimal(total), 4, HALF_UP);
    }
}
