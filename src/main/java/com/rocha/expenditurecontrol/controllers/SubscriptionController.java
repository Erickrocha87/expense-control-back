package com.rocha.expenditurecontrol.controllers;

import com.rocha.expenditurecontrol.dtos.PageResponseDTO;
import com.rocha.expenditurecontrol.dtos.SubscriptionRequestDTO;
import com.rocha.expenditurecontrol.dtos.SubscriptionResponseDTO;
import com.rocha.expenditurecontrol.services.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<SubscriptionResponseDTO> createSubscription(@Valid @RequestBody SubscriptionRequestDTO subscription) {
        SubscriptionResponseDTO subs = subscriptionService.createSubscription(subscription);
        if (subs == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(subs);
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO<SubscriptionResponseDTO>> getAllSubscriptions(@RequestParam(required = false) String status, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "7") int size) {
        System.out.println(status);
        if (status != null) {
            if (!status.equals("ALL")) {
            PageResponseDTO<SubscriptionResponseDTO> subsFiltered = subscriptionService.getByStatus(status, page, size);
            System.out.println(subsFiltered);
            return ResponseEntity.ok().body(subsFiltered);
            }
        }
        PageResponseDTO<SubscriptionResponseDTO> subs = subscriptionService.getUserSubscriptions(page, size);
        return ResponseEntity.ok().body(subs);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        subscriptionService.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionResponseDTO> updateSubscription(@PathVariable Long id, @Valid @RequestBody SubscriptionRequestDTO subscription) {
        SubscriptionResponseDTO subs = subscriptionService.updateSubscription(id, subscription);
        return ResponseEntity.ok().body(subs);
    }
}
