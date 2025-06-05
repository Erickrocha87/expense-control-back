package com.rocha.expenditurecontrol.repositories;

import com.rocha.expenditurecontrol.dtos.subscription.SubscriptionRequestDTO;
import com.rocha.expenditurecontrol.dtos.subscription.SubscriptionResponseDTO;
import com.rocha.expenditurecontrol.dtos.user.UserRequestDTO;
import com.rocha.expenditurecontrol.entities.Subscription;
import com.rocha.expenditurecontrol.entities.User;
import com.rocha.expenditurecontrol.entities.enums.SubscriptionFrequency;
import com.rocha.expenditurecontrol.entities.enums.SubscriptionStatus;
import jdk.jfr.Frequency;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SubscriptionRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Test
    @DisplayName("Deve retornar assinaturas com vencimento na data atual para o usuário")
    void returnSubscriptionsExpiringTodayForUser() {

        User user = new User();
        user.setEmail("teste@exemplo.com");
        user.setPassword("123456");
        user.setUsername("Teste");

        User savedUser = userRepository.save(user);

        Subscription subscription = new Subscription();
        subscription.setServiceName("Netflix");
        subscription.setPrice(BigDecimal.valueOf(49.90));
        subscription.setFrequency(SubscriptionFrequency.MONTHLY);
        subscription.setStatus(SubscriptionStatus.LATE);
        subscription.setDueDate(LocalDate.now());
        subscription.setUser(savedUser);

        subscriptionRepository.save(subscription);

        List<Subscription> result = subscriptionRepository
                .getExpiringSubscriptionsForUser(LocalDate.now(), savedUser.getId());

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getServiceName()).isEqualTo("Netflix");
    }

    @Test
    @DisplayName("Deve retornar as assinaturas do usuário")
    void returnSubscriptionsFindByUser() {
        User user = new User();
        user.setEmail("teste@exemplo.com");
        user.setPassword("123456");
        user.setUsername("Teste");

        User savedUser = userRepository.save(user);

        Subscription subscription = new Subscription();
        subscription.setServiceName("Netflix");
        subscription.setPrice(BigDecimal.valueOf(49.90));
        subscription.setFrequency(SubscriptionFrequency.MONTHLY);
        subscription.setStatus(SubscriptionStatus.LATE);
        subscription.setDueDate(LocalDate.now());
        subscription.setUser(savedUser);

        subscriptionRepository.save(subscription);

        List<SubscriptionResponseDTO> result  = subscriptionRepository.findByUser(savedUser);

        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("Deve retornar as assinaturas do usuário paginadas")
    void returnSubscriptionsPaginateFindByUser() {

        User user = new User();
        user.setEmail("teste@exemplo.com");
        user.setPassword("123456");
        user.setUsername("Teste");

        User savedUser = userRepository.save(user);

        Subscription subscription = new Subscription();
        subscription.setServiceName("Netflix");
        subscription.setPrice(BigDecimal.valueOf(49.90));
        subscription.setFrequency(SubscriptionFrequency.MONTHLY);
        subscription.setStatus(SubscriptionStatus.LATE);
        subscription.setDueDate(LocalDate.now());
        subscription.setUser(savedUser);

        subscriptionRepository.save(subscription);

        Pageable pageable = PageRequest.of(0, 10);

        Page<SubscriptionResponseDTO> subs = subscriptionRepository.findSubscriptionsByUser(savedUser, pageable);
        assertThat(subs).isNotEmpty();
    }

    @Test
    @DisplayName("Deve retornar as assinaturas paginadas com base no status passado")

    void returnSubscriptionsFindByStatus() {
        User user = new User();
        user.setEmail("teste@exemplo.com");
        user.setPassword("123456");
        user.setUsername("Teste");

        User savedUser = userRepository.save(user);

        Subscription subscription = new Subscription();
        subscription.setServiceName("Netflix");
        subscription.setPrice(BigDecimal.valueOf(49.90));
        subscription.setFrequency(SubscriptionFrequency.MONTHLY);
        subscription.setStatus(SubscriptionStatus.LATE);
        subscription.setDueDate(LocalDate.now());
        subscription.setUser(savedUser);

        subscriptionRepository.save(subscription);
        Pageable pegeable = PageRequest.of(0, 10);

        Page<Subscription> subs = subscriptionRepository.findByStatus(SubscriptionStatus.LATE, savedUser.getId(), pegeable);
        assertThat(subs).isNotEmpty();

    }
}