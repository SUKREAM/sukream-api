package com.sukream.sukream.mypage.domain.entity;

import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
public class Review {
    private Long id;
    private User reviewer;
    private User seller;
    private Product product;

    private String content;
    //판매자 별점
    private int rating;
    //상품품질평가

    private LocalDateTime createTime;
}
