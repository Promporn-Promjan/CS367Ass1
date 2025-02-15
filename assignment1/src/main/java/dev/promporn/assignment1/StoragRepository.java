package dev.promporn.assignment1;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface StoragRepository extends JpaRepository<Storge, Long> {
    List<Storge> findByBorrowed(Boolean borrowed);
    List<Storge> findByOwnerNameContainingIgnoreCase(String ownerName);
    List<Storge> findByBorrowerNameContainingIgnoreCase(String borrowerName);
}

