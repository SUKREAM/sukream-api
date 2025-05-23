package com.sukream.sukream.domains.review.domain.entity;

import com.sukream.sukream.domains.product.entity.Product;
import com.sukream.sukream.domains.user.domain.entity.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name="review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //리뷰 여러개 <-사용자 하나 가능
    @JoinColumn(name="writer_id", nullable=false) // 테이블에 writer_id로 외래키(User 테이블)
    private Users writer;

    @OneToOne(fetch = FetchType.LAZY) // 상품 하나 <-> 리뷰 하나
    @JoinColumn(name="product_id", nullable=false)
    private Product product;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false, length = 1000)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QualityAssesment qualityAssessment;
}
