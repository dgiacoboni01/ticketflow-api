package com.ingressos.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ingressos.api.model.Ingresso;

public interface IngressoRepository extends JpaRepository<Ingresso, Long> {
}