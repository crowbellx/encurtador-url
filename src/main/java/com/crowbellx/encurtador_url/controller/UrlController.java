package com.crowbellx.encurtador_url.controller;

import com.crowbellx.encurtador_url.model.Url;
import com.crowbellx.encurtador_url.service.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/urls")
public class UrlController {

    private UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<Url> shortenUrl(@RequestParam(defaultValue = "http://example.com") String originalUrl) {
        Url url = new Url();

        if (urlService.findByOriginalUrl(originalUrl).isPresent()){
             url = urlService.findByOriginalUrl(originalUrl).get();
             return ResponseEntity.ok(url);
        }else{
            url = urlService.shortenUrl(originalUrl);
            return ResponseEntity.ok(url);
        }
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity redirect(@PathVariable String shortUrl) {
        return urlService.findByShortUrl(shortUrl)
                .map(url -> ResponseEntity.status(302).location(URI.create(url.getOriginalUrl())).build())
                .orElse(ResponseEntity.notFound().build());
    }
}
