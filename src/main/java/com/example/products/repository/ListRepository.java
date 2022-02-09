package com.example.products.repository;

import com.example.products.model.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ListRepository extends JpaRepository<List, Long> {
    Optional<List> findByName(String name);
}
