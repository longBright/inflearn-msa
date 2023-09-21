package com.example.userservice.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ResponseOrder {
    private String productId;
    private Integer qty;
    private Integer uintPrice;
    private Date createdAt;
    private String orderId;
}
