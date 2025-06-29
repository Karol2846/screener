package com.stock.screener.application.domain.model;

import com.stock.screener.application.exception.DomainException;
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
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true, chain = true)
@Table(name = "fundamental_data")
public class FundamentalData {

    @Id
    private UUID id;

    @Column(insertable = false, updatable = false)
    private String symbol;

    private Long marketCap;
    private Long enterpriseValue;        // (totalEnterprise)

    private BigDecimal peRatio;
    private BigDecimal pegRatio;
    private BigDecimal pbRatio;
    private BigDecimal peForward;
    private BigDecimal evToEbitda;
    private BigDecimal evToSales;

    // Prognozy i cele
    //DOCS: wszystko price target: https://rapidapi.com/apidojo/api/seeking-alpha/playground/apiendpoint_cb5cc243-13ed-4ebf-93bc-c93ee5076b6d
    private BigDecimal priceTargetConsensus;
    private BigDecimal priceTargetHigh;
    private BigDecimal priceTargetLow;

    private BigDecimal eps;                 //(estimateEps)

    @CreatedDate
    private LocalDate createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "symbol")
    private Stock stock;

    public Long ebitda() {
        if(evToEbitda == null || enterpriseValue == null) {
            throw new DomainException("EBITDA cannot be calculated: evToEbitda or enterpriseValue is null");
        }
        return Math.divideExact(enterpriseValue, evToEbitda.longValue());
    }

    public Long revenue() {
        if(evToSales == null || enterpriseValue == null) {
            throw new DomainException("REVENUE cannot be calculated: evToSales or enterpriseValue is null");
        }
        return Math.divideExact(enterpriseValue, evToSales.longValue());
    }
}
