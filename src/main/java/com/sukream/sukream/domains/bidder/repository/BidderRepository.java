package com.sukream.sukream.domains.bidder.repository;

import com.sukream.sukream.domains.bidder.entity.Bidder;
import com.sukream.sukream.domains.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidderRepository extends JpaRepository<Bidder, Long> {
    List<Bidder> findByProduct_ProductIdOrderByPriceDesc(Long productId);

    List<Bidder> findAllByUser(User user);

}
