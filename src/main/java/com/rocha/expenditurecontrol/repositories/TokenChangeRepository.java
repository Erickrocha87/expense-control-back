package com.rocha.expenditurecontrol.repositories;

import com.rocha.expenditurecontrol.entities.TokenChangePassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenChangeRepository extends JpaRepository<TokenChangePassword, Long> {

    TokenChangePassword findByToken(String token);
}
