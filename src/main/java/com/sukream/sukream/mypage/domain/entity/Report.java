package com.sukream.sukream.mypage.domain.entity;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class Report {
    private Long id;
    private User reporter;
    private User reportedUser;
    private Product product;

    //신고이유 (선택)

    private LocalDate reportDate;

}
