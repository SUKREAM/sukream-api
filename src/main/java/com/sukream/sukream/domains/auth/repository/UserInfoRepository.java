package com.sukream.sukream.domains.auth.repository;


import com.sukream.sukream.domains.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
}
