package com.sukream.sukream.domains.review.repository;

import com.sukream.sukream.domains.review.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByWriterIdAndProductId(Long writerId, Long productId);
}
