package com.csit214.repository;

import com.csit214.models.FrequentFlyerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<FrequentFlyerAccount, Long> {
    Optional<FrequentFlyerAccount> findByPassportNumber(String passportNumber);

    Optional<FrequentFlyerAccount> findByUsername(String username);

    Boolean existsByPassportNumber(String passportNumber);

    Boolean existsByUsername(String username);

    @Override
    List<FrequentFlyerAccount> findAll();
}
