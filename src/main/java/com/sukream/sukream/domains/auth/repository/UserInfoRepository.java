package com.sukream.sukream.domains.auth.repository;


import com.sukream.sukream.domains.user.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<Users, Long> {
    Optional<Users> findUsersByEmail(String email);

    Optional<Users> findUsersByPhoneNumber(String phoneNumber);


}
