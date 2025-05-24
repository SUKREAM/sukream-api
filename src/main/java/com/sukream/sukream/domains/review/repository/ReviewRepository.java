package com.sukream.sukream.domains.review.repository;

import com.sukream.sukream.domains.review.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>{
    boolean existsByWriterIdAndProductId(Long writerId, Long productId);

    List<Review> findByProduct_Owner_Id(Long ownerId);
}
