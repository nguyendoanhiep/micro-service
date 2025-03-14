package com.example.productservice.dto.request;

import lombok.Data;

import java.util.List;
@Data
public class AssignCusRequest {
    Long voucherId;
    List<Long> customerIds;
}
