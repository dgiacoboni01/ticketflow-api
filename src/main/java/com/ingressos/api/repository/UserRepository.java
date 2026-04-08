package com.ingressos.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ingressos.api.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
