package com.stock.screener.adapter.web.seeking.alpha.model.summary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SummaryAttributes(
        BigDecimal cash,
        @JsonProperty("chgp1y") BigDecimal percentChange1Year,
        @JsonProperty("chgp3m") BigDecimal percentChange3Months,
        @JsonProperty("chgp6m") BigDecimal percentChange6Months,
        @JsonProperty("chgp9m") BigDecimal percentChange9Months,
        String companyName,
        BigDecimal estimateEps,
        BigDecimal evEbitda,
        BigDecimal evSales,
        @JsonProperty("lastClosePriceEarningsRatio") BigDecimal pe,
        BigDecimal marketCap,
        @JsonProperty("peRatioFwd") BigDecimal peForward,
        @JsonProperty("priceBook") BigDecimal pb,
        String sectorname,
        @JsonProperty("totalEnterprise") BigDecimal enterpriseValue
) {

}
