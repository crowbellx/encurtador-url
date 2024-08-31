package com.crowbellx.encurtador_url.repository;

import com.crowbellx.encurtador_url.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url,Long> {
    Optional<Url> findByShortUrl(String curtaUrl);
}
