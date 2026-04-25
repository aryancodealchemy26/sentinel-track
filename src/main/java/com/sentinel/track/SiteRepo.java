package com.sentinel.track;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteRepo extends JpaRepository<Site, Long> {
    // Spring Boot will automatically write the SQL for us!
}