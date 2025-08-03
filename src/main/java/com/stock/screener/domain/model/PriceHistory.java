package com.stock.screener.domain.model;

import com.stock.screener.application.port.command.CurrentPriceCommand;
import jakarta.persistence.Column;
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
@Table(name = "price_history")
@EntityListeners(AuditingEntityListener.class)
public class PriceHistory {

    //DOCS
    // price dla każdej firmy osobno: https://finnhub.io/api/v1/quote?symbol=AAPL&token=d1fv3uhr01qk4ao003i0d1fv3uhr01qk4ao003ig
    // moving averages dla wszystkich firm na raz: https://rapidapi.com/apidojo/api/seeking-alpha/playground/apiendpoint_3ebb08a0-39fd-4b3f-ad28-faca7a9fdfab

    @EmbeddedId
    private CompoundId id;

    private BigDecimal currentPrice;

    @Column(name = "average_50_price")
    private BigDecimal averagePrice50Days;

    @Column(name = "average_100_price")
    private BigDecimal averagePrice100Days;

    @Column(name = "average_200_price")
    private BigDecimal averagePrice200Days;

    @MapsId("symbol")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "symbol", nullable = false)
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

    @Override
    public String toString() {
        return "PriceHistory{" +
                "id=" + id +
                ", currentPrice=" + currentPrice +
                ", averagePrice50Days=" + averagePrice50Days +
                ", averagePrice100Days=" + averagePrice100Days +
                ", averagePrice200Days=" + averagePrice200Days +
                '}';
    }
}
