package com.stock.screener.application.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fundamental_data")
public class FundamentalData {

    @Id
    private UUID id;

    @Column(insertable = false, updatable = false)
    private String symbol;

    private Long marketCap;
    private Long ebitda;
    private Long revenue;
    private Long enterpriseValue;

    // wskaźniki wyceny
    private BigDecimal peRatio;
    private BigDecimal pegRatio;
    private BigDecimal peForward;
    private BigDecimal evToEbitda;
    private BigDecimal psForward;

    // Prognozy i cele
    private BigDecimal priceTarget;
    private BigDecimal eps;

    @CreatedDate
    private OffsetDateTime createdAt;

    // Relacja
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "symbol")
    private Stock stock;

    public void calculateEv() {
        if (evToEbitda != null && ebitda != null) {
            this.enterpriseValue = evToEbitda.multiply(new BigDecimal(ebitda)).longValue();
        }
    }
}
