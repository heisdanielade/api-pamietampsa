package com.github.heisdanielade.pamietampsa.repository;

import com.github.heisdanielade.pamietampsa.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Long> {

    Optional<AppUser> findByEmail(String email);
    Optional<AppUser> findByVerificationCode(String verificationCode);

    boolean existsByEmail(String email);

}
