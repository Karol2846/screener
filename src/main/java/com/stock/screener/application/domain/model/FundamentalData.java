package com.stock.screener.application.domain.model;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static jakarta.persistence.FetchType.LAZY;

@Slf4j
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fundamental_data")
@EntityListeners(AuditingEntityListener.class)
public class FundamentalData {

    @EmbeddedId
    private CompoundId id;

    private Long marketCap;
    private Long enterpriseValue;
    private Long ebitda;
    private Long revenue;
    private BigDecimal eps;
    private BigDecimal forwardEps3Y;

    private BigDecimal peRatio;
    private BigDecimal pegForward;
    private BigDecimal pbRatio;
    private BigDecimal peForward;
    private BigDecimal evEbitda;
    private BigDecimal evSales;

    private BigDecimal priceTargetConsensus;
    private BigDecimal priceTargetHigh;
    private BigDecimal priceTargetLow;

    @MapsId("symbol")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "symbol", nullable = false)
    private Stock stock;

    public Long calculateEbitda() {
        if (evEbitda == null || enterpriseValue == null) {
            log.debug("evEbitda or enterpriseValue is null, ebitda set as null");
            return null;
        }
        var ebitda = Math.divideExact(enterpriseValue, evEbitda.longValue());
        this.setEbitda(ebitda);
        return ebitda;
    }

    public Long calculateRevenue() {
        if (evSales == null || enterpriseValue == null) {
            log.debug("evSales or enterpriseValue is null, revenue set as null");
            return null;
        }
        var revenue = Math.divideExact(enterpriseValue, evSales.longValue());
        this.setRevenue(revenue);
        return revenue;
    }

    public BigDecimal calculatePegForward() {
        if(peForward == null || forwardEps3Y == null) {
            log.debug("peForward or forwardEps3Y is null, pegForward set as null");
            return null;
        }

        var pegForward = peForward.divide(forwardEps3Y, RoundingMode.HALF_UP);
        this.setPegForward(pegForward);
        return pegForward;
    }
}
