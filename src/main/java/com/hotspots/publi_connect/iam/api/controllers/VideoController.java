package com.hotspots.publi_connect.iam.api.controllers;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

/**
 * TODO: This is a dummy controller for MVP demonstration.
 * Replace with a real VideoService that streams from AWS S3 in the future.
 */
@RestController
@RequestMapping("/videos")
public class VideoController {

    // TODO: Remove this redirect and implement actual binary streaming
    @GetMapping("/dummy-ad")
    public Mono<Void> getDummyAd(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.TEMPORARY_REDIRECT);
        response.getHeaders().setLocation(URI.create("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"));
        return response.setComplete();
    }
}
