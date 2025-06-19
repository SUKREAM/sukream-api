package com.sukream.sukream.domains.mypage.service;

import com.sukream.sukream.domains.mypage.domain.dto.SalesResponseDto;
import com.sukream.sukream.domains.product.entity.Product;
import com.sukream.sukream.domains.product.repository.ProductRepository;
import com.sukream.sukream.domains.user.domain.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MySalesQueryService {
    private final ProductRepository productRepository;

    public List<SalesResponseDto> getMySales(Users user) {
        List<Product> myProducts = productRepository.findAllByOwner(user);
        List<SalesResponseDto> result = new ArrayList<>();

        for(Product product : myProducts) {
            String base64Image = null;
            if (product.getImage() != null) {
                base64Image = Base64.getEncoder().encodeToString(product.getImage());
            }

            SalesResponseDto dto = SalesResponseDto.builder()
                    .productId(product.getId())
                    .productName(product.getTitle())
                    .productImage(base64Image)
                    .status(product.getStatus())
                    .createdAt(product.getCreatedAt())
                    .build();

            result.add(dto);
        }


        return result;
    }
}
