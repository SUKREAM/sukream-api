package com.sukream.sukream.domains.bidder.repository;

import com.sukream.sukream.domains.bidder.entity.Bidder;
import com.sukream.sukream.domains.bidder.entity.BidderStatus;
import com.sukream.sukream.domains.product.entity.Product;
import com.sukream.sukream.domains.user.domain.entity.Users;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BidderRepository extends JpaRepository<Bidder, Long> {
    List<Bidder> findByProduct_IdOrderByPriceDesc(Long productId);
    List<Bidder> findAllByUser(Users user);

    // 최고 입찰가 조회 (Optional<Long> 반환)
    @Query("SELECT MAX(b.price) FROM Bidder b WHERE b.product.id = :productId")
    Optional<Long> findHighestBidPriceByProductId(@Param("productId") Long productId);

    // 특정 상품-사용자에 대한 입찰 존재 여부 확인
    boolean existsByProductAndUser(Product product, Users user);

    //특정 경매 - userid, productid, isAwarded 통해 검증
    boolean existsByUser_IdAndProduct_IdAndStatus(Long userId, Long productId, BidderStatus status);

    // 입찰 수 계산
    int countByProduct_Id(Long productId);

    // 최고 입찰자 1명 조회 (가격 내림차순 정렬)
    Optional<Bidder> findTopByProduct_IdAndStatusOrderByPriceDesc(Long productId, BidderStatus status);
}
