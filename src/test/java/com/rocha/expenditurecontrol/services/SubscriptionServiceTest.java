package com.rocha.expenditurecontrol.services;

import com.rocha.expenditurecontrol.dtos.subscription.PageResponseDTO;
import com.rocha.expenditurecontrol.dtos.subscription.SubscriptionRequestDTO;
import com.rocha.expenditurecontrol.dtos.subscription.SubscriptionResponseDTO;
import com.rocha.expenditurecontrol.entities.Subscription;
import com.rocha.expenditurecontrol.entities.User;
import com.rocha.expenditurecontrol.entities.enums.SubscriptionFrequency;
import com.rocha.expenditurecontrol.entities.enums.SubscriptionStatus;
import com.rocha.expenditurecontrol.mapper.SubscriptionMapper;
import com.rocha.expenditurecontrol.repositories.SubscriptionRepository;
import com.rocha.expenditurecontrol.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    //simula um mock
    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    //injeta o mock no serviço
    @InjectMocks
    private SubscriptionService subscriptionService;

    @Test
    @DisplayName("Deve deletar uma assinatura existente pelo ID")
    void shouldDeleteById() {

        Long id = 1L;
        Subscription subscription = new Subscription();
        subscription.setId(id);

        //verifica quando esse metodo ser chamado o retorno seja um subs
        Mockito.when(subscriptionRepository.getReferenceById(id)).thenReturn(subscription);

        subscriptionService.deleteById(id);

        //verifica se teve a execução desse método
        Mockito.verify(subscriptionRepository).getReferenceById(id);
        Mockito.verify(subscriptionRepository).delete(subscription);
        //verifica se além dos que foram chamados, nenhum outro método sejá chamado
        Mockito.verifyNoMoreInteractions(subscriptionRepository);
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException se a assinatura não existir")
    void shouldThrowExceptionWhenSubscriptionNotFound() {

        Long id = 999L;
        //quando esse método for chamado, deve retornar uma exceção
        Mockito.when(subscriptionRepository.getReferenceById(id))
                .thenThrow(new EntityNotFoundException("Assinatura não encontrada"));

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> subscriptionService.deleteById(id)
        );

        //verifica se o que vai retornar é o mesmo esperado
        assertEquals("Assinatura não encontrada", exception.getMessage());

        //verifica se o método foi chamado
        Mockito.verify(subscriptionRepository).getReferenceById(id);
        //veerifica se o método deletar não foi chamado pois ele não deve ser chamado
        Mockito.verify(subscriptionRepository, Mockito.never()).delete(any());
    }


    @Test
    @DisplayName("Deve retornar uma lista paginada de assinaturas expiradas")
    void getExpiringSubscriptionsForUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("teste@exemplo.com");
        user.setPassword("123456");
        user.setUsername("Teste");


        Subscription subscription = new Subscription();
        subscription.setServiceName("Netflix");
        subscription.setPrice(BigDecimal.valueOf(49.90));
        subscription.setFrequency(SubscriptionFrequency.MONTHLY);
        subscription.setStatus(SubscriptionStatus.LATE);
        subscription.setDueDate(LocalDate.now());

        LocalDate today = LocalDate.now(ZoneId.of("America/Sao_Paulo"));

        List<Subscription> subscriptions = List.of(subscription);

        Mockito.when(subscriptionRepository.getExpiringSubscriptionsForUser(today, user.getId())).thenReturn(subscriptions);

        List<Subscription> result = subscriptionService.getExpiringSubscriptionsForUser(user);

        assertEquals("Netflix", result.get(0).getServiceName());
        assertNotNull(result);

        Mockito.verify(subscriptionRepository).getExpiringSubscriptionsForUser(today, user.getId());
        Mockito.verifyNoMoreInteractions(subscriptionRepository);
    }

    @Test
    @DisplayName("Deve criar uma assinatura")
    void createSubscription() {

        User user = new User();
        user.setEmail("teste@exemplo.com");
        user.setPassword("123456");
        user.setUsername("Teste");

        SubscriptionRequestDTO dto = new SubscriptionRequestDTO(
                1L,
                "Netflix",
                BigDecimal.valueOf(49.90),
                LocalDate.now(),
                SubscriptionFrequency.MONTHLY,
                SubscriptionStatus.LATE,
                user
        );

        Subscription subscription = new Subscription();
        subscription.setId(1L);
        subscription.setServiceName(dto.getServiceName());
        subscription.setPrice(dto.getPrice());
        subscription.setFrequency(dto.getFrequency());
        subscription.setStatus(dto.getStatus());
        subscription.setDueDate(dto.getDueDate());
        subscription.setUser(user);

        Mockito.when(subscriptionRepository.save(any(Subscription.class))).thenReturn(subscription);
        Mockito.when(userService.getAuthUser()).thenReturn(user);

        SubscriptionResponseDTO result = subscriptionService.createSubscription(dto); //não esquecer de chamar o serviço sempre também

        assertEquals("Netflix", result.serviceName());
        assertNotNull(result);
        Mockito.verify(subscriptionRepository).save(any(Subscription.class));
        Mockito.verify(userService).getAuthUser();
        Mockito.verifyNoMoreInteractions(subscriptionRepository);

    }

    @Test
    @DisplayName("Deve retornar uma lista de assinaturas")
    void getUserSubscriptions() {

        User user = new User();
        user.setId(1L);
        user.setEmail("Erick@gmail.com");

        Subscription subscription = new Subscription();
        subscription.setServiceName("Netflix");
        subscription.setPrice(BigDecimal.valueOf(49.90));
        subscription.setFrequency(SubscriptionFrequency.MONTHLY);
        subscription.setStatus(SubscriptionStatus.LATE);
        subscription.setDueDate(LocalDate.now());
        subscription.setUser(user);

        BigDecimal total = BigDecimal.valueOf(49.90);
        Pageable pageable = PageRequest.of(0, 10);

        List<Subscription> subs = List.of(subscription);
        List<SubscriptionResponseDTO> subsDto = subs.stream().map(SubscriptionMapper::toSubscriptionResponseDTO).toList();

        Page<SubscriptionResponseDTO> pageSubs = new PageImpl<>(subsDto, pageable, 1);

        Mockito.when(userService.getAuthUser()).thenReturn(user);
        Mockito.when(subscriptionRepository.findByUser(user)).thenReturn(subsDto);
        Mockito.when(subscriptionRepository.findSubscriptionsByUser(user, pageable)).thenReturn(pageSubs);

        PageResponseDTO<SubscriptionResponseDTO> result = subscriptionService.getUserSubscriptions(0, 10);

        Mockito.verify(userService).getAuthUser();
        Mockito.verify(subscriptionRepository).findSubscriptionsByUser(user, pageable);
        Mockito.verify(subscriptionRepository).findByUser(user);
        Mockito.verifyNoMoreInteractions(subscriptionRepository);

        assertNotNull(result);
        assertEquals("Netflix", result.content().get(0).serviceName());

    }
}