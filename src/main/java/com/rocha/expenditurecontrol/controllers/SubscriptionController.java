package com.rocha.expenditurecontrol.controllers;

import com.rocha.expenditurecontrol.dtos.PageResponseDTO;
import com.rocha.expenditurecontrol.dtos.SubscriptionRequestDTO;
import com.rocha.expenditurecontrol.dtos.SubscriptionResponseDTO;
import com.rocha.expenditurecontrol.entities.Subscription;
import com.rocha.expenditurecontrol.services.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<SubscriptionResponseDTO> createSubscription(@Valid @RequestBody SubscriptionRequestDTO subscription) {
        SubscriptionResponseDTO subs = subscriptionService.createSubscription(subscription);
        return ResponseEntity.status(HttpStatus.CREATED).body(subs);
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO<SubscriptionResponseDTO>> getAllSubscriptions(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size ) {
        PageResponseDTO<SubscriptionResponseDTO> subs = subscriptionService.getUserSubscriptions(page, size);
        return ResponseEntity.ok().body(subs);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        subscriptionService.deleteById(id);
    }

    @PutMapping("/{id}")
        public ResponseEntity<Subscription> updateSubscription(@PathVariable Long id, @Valid @RequestBody Subscription subscription) {
        Subscription subs = subscriptionService.updateSubscription(id, subscription);
        return ResponseEntity.ok().body(subs);
    } //tirar a entidade da controller
}
