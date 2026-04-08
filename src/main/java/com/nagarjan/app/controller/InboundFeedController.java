package com.nagarjan.app.controller;

import com.nagarjan.app.services.InboundFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inbound")
@RequiredArgsConstructor
public class InboundFeedController {

    private final InboundFeedService inboundService;

    @GetMapping
    public ResponseEntity<?> getInboundFeed() {
        return ResponseEntity.ok(inboundService.getInboundFeed());
    }

    @GetMapping("/{id}/ingest")
    public ResponseEntity<?> getIngestData(@PathVariable Long id) {
        return ResponseEntity.ok(inboundService.getIngestDetails(id));
    }
}
