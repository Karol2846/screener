package com.stock.screener.application.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true, chain = true)
@Table(name = "stock")
public class Stock {
    //DOCS: kolejny krok to tworzenie połączenia z YH Finance API
    // i koknfiguracja WebClient-a
    // później stworzenie modelu DTOs i mapperów

    // DOCS: pobranie wszystkich firm
    //  https://rapidapi.com/apidojo/api/seeking-alpha/playground/apiendpoint_0b9a23fa-b712-4efe-bf5b-217148378179

    @Id
    private String symbol;
    private Long seekingAlphaTrackerId;
    private String companyName;
    private String sector;

    @CreatedDate
    private LocalDate createdAt;

    @LastModifiedDate
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "stock", cascade = ALL, fetch = LAZY)
    private List<PriceHistory> priceHistory;

    @OneToMany(mappedBy = "stock", cascade = ALL, fetch = LAZY)
    private List<FundamentalData> fundamentalData;

    @OneToMany(mappedBy = "stock", cascade = ALL, fetch = LAZY)
    private List<AnalystRecommendation> analystRecommendations;
}
