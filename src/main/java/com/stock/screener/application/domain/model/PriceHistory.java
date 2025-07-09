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
import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;
import static java.math.RoundingMode.HALF_UP;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "price_history")
public class PriceHistory {

    //DOCS
    // price dla każdej firmy osobno: https://finnhub.io/api/v1/quote?symbol=AAPL&token=d1fv3uhr01qk4ao003i0d1fv3uhr01qk4ao003ig
    // moving averages dla wszystkich firm na raz: https://rapidapi.com/apidojo/api/seeking-alpha/playground/apiendpoint_3ebb08a0-39fd-4b3f-ad28-faca7a9fdfab

    @Id
    @Column(insertable = false, updatable = false)
    private String symbol;

    private BigDecimal currentPrice; // (pc - previous close price)

    @Column(name = "average_50_price", precision = 12, scale = 4)
    private BigDecimal average50Price;

    @Column(name = "average_100_price", precision = 12, scale = 4)
    private BigDecimal average100Price;

    @Column(name = "average_200_price", precision = 12, scale = 4)
    private BigDecimal average200Price;

    @CreatedDate
    private LocalDate createdAt;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "symbol", referencedColumnName = "symbol")
    private Stock stock;

    public BigDecimal upside(BigDecimal priceTarget) {
        if (currentPrice == null || priceTarget == null ||
                currentPrice.compareTo(BigDecimal.ZERO) <= 0) {
            return null;
        }

        return priceTarget.subtract(currentPrice)
                .divide(currentPrice, 4, HALF_UP)
                .multiply(new BigDecimal("100"));
    }

    public BigDecimal psForward(BigDecimal eps) {
        if (currentPrice == null || eps == null ||
                currentPrice.compareTo(BigDecimal.ZERO) <= 0 ||
                eps.compareTo(BigDecimal.ZERO) <= 0) {
            return null;
        }

        return currentPrice.divide(eps, 4, HALF_UP);
    }
}
