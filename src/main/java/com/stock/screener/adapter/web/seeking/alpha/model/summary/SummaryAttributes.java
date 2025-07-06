package com.stock.screener.adapter.web.seeking.alpha.model.summary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SummaryAttributes(
        BigDecimal cash,
        @JsonProperty("chgp3m") BigDecimal percentChange3Months,
        @JsonProperty("chgp6m") BigDecimal percentChange6Months,
        @JsonProperty("chgp9m") BigDecimal percentChange9Months,
        @JsonProperty("chgp1y") BigDecimal percentChange12Months,
        String companyName,
        BigDecimal estimateEps,
        BigDecimal evEbitda,
        BigDecimal evSales,
        BigDecimal lastClosePriceEarningsRatio,
        BigDecimal marketCap,
        BigDecimal peRatioFwd,
        BigDecimal priceBook,
        String sectorname,
        BigDecimal totalEnterprise,
        BigDecimal dilutedEpsGrowth
) { }
