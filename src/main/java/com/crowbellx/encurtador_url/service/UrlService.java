package com.crowbellx.encurtador_url.service;

import com.crowbellx.encurtador_url.model.Url;
import com.crowbellx.encurtador_url.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

@Service
public class UrlService {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int MIN_LENGTH = 5;
    private static final int MAX_LENGTH = 10;

    private UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    private String generateShortUrl(int length){
        SecureRandom random = new SecureRandom();
        StringBuilder shortUrl = new StringBuilder(length);
        for(int i = 0; i < length; i++){
            shortUrl.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return shortUrl.toString();
    }

    public Url shortenUrl(String originalUrl) {
        int length = MIN_LENGTH + new SecureRandom().nextInt(MAX_LENGTH - MIN_LENGTH + 1);
        String shortUrl = generateShortUrl(length);
        Url url = new Url(originalUrl,shortUrl);
        return urlRepository.save(url);
    }

    public Optional<Url> findByShortUrl(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl);
    }
}
