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
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "price_history")
public class PriceHistory {

    @Id
    private UUID id;

    @Column(insertable = false, updatable = false)
    private String symbol;

    private BigDecimal currentPrice;

    @Column(name = "average_50_price", precision = 12, scale = 4)
    private BigDecimal average50Price;

    @Column(name = "average_200_price", precision = 12, scale = 4)
    private BigDecimal average200Price;

    @CreatedDate
    private OffsetDateTime createdAt;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "symbol", referencedColumnName = "symbol")
    private Stock stock;

    public BigDecimal calculateUpside(BigDecimal priceTarget) {
        if (currentPrice == null || priceTarget == null ||
                currentPrice.compareTo(BigDecimal.ZERO) <= 0) {
            return null;
        }

        return priceTarget.subtract(currentPrice)
                .divide(currentPrice, 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"));
    }
}
