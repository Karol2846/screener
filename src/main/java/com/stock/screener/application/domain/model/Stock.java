package com.stock.screener.application.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stock")
@EntityListeners(AuditingEntityListener.class)
public class Stock {

    @Id
    private String symbol;
    private Integer seekingAlphaTrackerId;
    private String companyName;
    private String sector;

    @CreatedDate
    private LocalDate createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "stock", cascade = ALL, fetch = LAZY)
    private List<PriceHistory> priceHistory;

    @OneToMany(mappedBy = "stock", cascade = ALL, fetch = LAZY)
    private List<FundamentalData> fundamentalData;

    @OneToMany(mappedBy = "stock", cascade = ALL, fetch = LAZY)
    private List<AnalystRecommendation> analystRecommendations;

    @Override
    public String toString() {
        return "Stock{" +
                "symbol='" + symbol + '\'' +
                ", seekingAlphaTrackerId=" + seekingAlphaTrackerId +
                ", companyName='" + companyName + '\'' +
                ", sector='" + sector + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

// TODO: chat with claude: https://claude.ai/chat/c3ba2324-e23d-49da-806d-72c37cb0f7f0
//  Ogólne podejście bardzo mi się podoba :)
//  Myślę ze cache rozwiąże problem - tym bardziej że nie będzie on duży. Raczje nie będę miał więcej niż 100 spółek. Jak na razie jestem w miejscu, w którym tworze pojedyncze serwisy do updateowania - później chciałem zrobić orchestrator który koordynowałby pracę między nimi. Myślisz że to podejście jest dobre, czy lepiej użyć applicationEventów?
//  Zastanawiam się też nad sensem 2 map - nie lepiej jedna? Właściwie, to zarówno klucze jak i wartości będą unikalne