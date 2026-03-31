package com.ingressos.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ingressos.api.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}