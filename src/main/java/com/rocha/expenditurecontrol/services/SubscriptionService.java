package com.rocha.expenditurecontrol.services;


import com.rocha.expenditurecontrol.dtos.PageResponseDTO;
import com.rocha.expenditurecontrol.dtos.SubscriptionRequestDTO;
import com.rocha.expenditurecontrol.dtos.SubscriptionResponseDTO;
import com.rocha.expenditurecontrol.entities.Subscription;
import com.rocha.expenditurecontrol.entities.User;
import com.rocha.expenditurecontrol.exceptions.SubscriptionNotFoundException;
import com.rocha.expenditurecontrol.mapper.SubscriptionMapper;
import com.rocha.expenditurecontrol.repositories.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final UserService userService;
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionResponseDTO createSubscription(SubscriptionRequestDTO subscription) {
        User user = userService.getAuthUser();
        subscription.setUser(user);
        Subscription subs = SubscriptionMapper.toSubscription(subscription);
        return SubscriptionMapper.toSubscriptionResponseDTO(subscriptionRepository.save(subs));
    }

    public void deleteById(Long id) {
        subscriptionRepository.deleteById(id);
    }

    public Subscription updateSubscription(Long id, Subscription subscription) {
        Subscription subs = subscriptionRepository.findById(id).orElseThrow(() -> new SubscriptionNotFoundException("Subscription not found. Id: " + id));
        if (subs != null) {
            subs.setId(id);
            subs.setServiceName(subscription.getServiceName());
            subs.setPrice(subscription.getPrice());
            subs.setDueDate(subscription.getDueDate());
            subs.setFrequency(subscription.getFrequency());
            subs.setStatus(subscription.getStatus());
            System.out.println();
            //return SubscriptionMapper.toSubscriptionResponseDTO(subscriptionRepository.save(subs));
            return subscriptionRepository.save(subs);
        }
        return null;

    }


    public PageResponseDTO<SubscriptionResponseDTO> getUserSubscriptions(int page, int size) {
        User user = userService.getAuthUser();
        Pageable pageable = PageRequest.of(page, size);

        List<SubscriptionResponseDTO> subs = subscriptionRepository.findByUser(user);
        BigDecimal total = subs.stream().map(SubscriptionResponseDTO::price).reduce(BigDecimal.ZERO, BigDecimal::add);
        Page<SubscriptionResponseDTO> subscriptionsPage = subscriptionRepository.findSubscriptionsByUser(user, pageable);
        return new PageResponseDTO<>(
                subscriptionsPage.getContent(),
                subscriptionsPage.getTotalPages(),
                subscriptionsPage.getTotalElements(),
                subscriptionsPage.getNumber(),
                subscriptionsPage.getSize(),
                total
        );
    }

    public List<Subscription> getExpiringSubscriptionsForUser(User user){
        LocalDate today = LocalDate.now(ZoneId.of("America/Sao_Paulo"));
        return subscriptionRepository.getExpiringSubscriptionsForUser(today, user.getId());
    }
}
