package com.rocha.expenditurecontrol.repositories;

import com.rocha.expenditurecontrol.dtos.SubscriptionResponseDTO;
import com.rocha.expenditurecontrol.entities.Subscription;
import com.rocha.expenditurecontrol.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<SubscriptionResponseDTO> findByUser(User user);

    Page<SubscriptionResponseDTO> findSubscriptionsByUser(User user, Pageable pageable);

    @Query(value = "SELECT * FROM tb_subscription s WHERE s.due_date = :currentDate AND  s.user_id = :userId", nativeQuery = true)
    List<Subscription> getExpiringSubscriptionsForUser(@Param("currentDate") LocalDate currentDate, @Param("userId") Long userId);

//    @Query(value = "SELECT * FROM tb_subscription s WHERE s.user_id = :userId ORDER BY s.service_name ASC", nativeQuery = true)
//  List<SubscriptionResponseDTO> findAllByUserId(@Param("userId") Long userId);

}
