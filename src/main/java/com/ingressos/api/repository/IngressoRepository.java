package com.ingressos.api.repository;

import com.ingressos.api.model.Ingresso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IngressoRepository extends JpaRepository<Ingresso, Long> {
    List<Ingresso> findByUsuarioId(Long usuarioId);
}
