package by.lobanov.linkshortenerservice.repository;

import by.lobanov.linkshortenerservice.model.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.*;

import java.time.*;
import java.util.*;

public interface LinkRepository extends JpaRepository<Link, Long> {

    Optional<Link> findByShortCode(String shortCode);

    @Modifying
    @Transactional
    @Query("DELETE FROM Link l WHERE l.expiresAt IS NOT NULL AND l.expiresAt < :now")
    void deleteExpiredLinks(LocalDateTime now);
}