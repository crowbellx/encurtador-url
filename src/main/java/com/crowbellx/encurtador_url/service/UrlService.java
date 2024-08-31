package com.crowbellx.encurtador_url.service;

import com.crowbellx.encurtador_url.model.Url;
import com.crowbellx.encurtador_url.repository.UrlRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

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
        Random random = new Random();
        StringBuilder shortUrl = new StringBuilder(length);
        for(int i = 0; i < length; i++){
            shortUrl.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return shortUrl.toString();
    }

    public Url shortenUrl(String originalUrl) {
        int length = MIN_LENGTH + new Random().nextInt(MAX_LENGTH - MIN_LENGTH + 1);
        String shortUrl = generateShortUrl(length);
        Url url = new Url(originalUrl,shortUrl);
        return urlRepository.save(url);
    }

    @Cacheable("urls")
    public Optional<Url> findByShortUrl(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl);
    }
    @Cacheable("urls")
    public Optional<Url> findByOriginalUrl(String originalUrl) {
        return urlRepository.findByOriginalUrl(originalUrl);
    }
}
