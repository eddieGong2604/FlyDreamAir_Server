package com.csit214.repository;

import com.csit214.models.Seating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatingRepository extends JpaRepository<Seating, Long> {
}
