package com.synapsis.shop.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.synapsis.shop.dbo.Transaction;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionListResponse {
    @JsonProperty("total_page")
    private Integer totalPage;

    @JsonProperty("size_page")
    private Integer sizePage;

    @JsonProperty("total_data")
    private Long totalData;

    @JsonProperty("data")
    private List<Transaction> data;

}
