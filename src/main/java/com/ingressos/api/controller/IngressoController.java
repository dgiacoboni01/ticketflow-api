package com.ingressos.api.controller;

import com.ingressos.api.model.Ingresso;
import com.ingressos.api.model.User;
import com.ingressos.api.repository.IngressoRepository;
import com.ingressos.api.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/ingressos")
@CrossOrigin
public class IngressoController {

    private final IngressoRepository repo;
    private final UserRepository userRepo;

    public IngressoController(IngressoRepository repo, UserRepository userRepo) {
        this.repo     = repo;
        this.userRepo = userRepo;
    }

    public record ComprarRequest(
        @NotBlank String evento,
        @NotNull @Positive Double preco
    ) {}

    /** ADMIN vê todos; usuário comum vê apenas os seus. */
    @GetMapping
    public List<Ingresso> listar(Authentication auth) {
        boolean isAdmin = auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) return repo.findAll();

        User usuario = userRepo.findByEmail(auth.getName()).orElseThrow();
        return repo.findByUsuarioId(usuario.getId());
    }

    /** Compra um ingresso para o usuário autenticado. */
    @PostMapping
    public ResponseEntity<Ingresso> criar(@Valid @RequestBody ComprarRequest req, Authentication auth) {
        User usuario = userRepo.findByEmail(auth.getName()).orElseThrow();

        Ingresso ingresso = new Ingresso();
        ingresso.setEvento(req.evento());
        ingresso.setPreco(req.preco());
        ingresso.setUsuario(usuario);

        Ingresso saved = repo.save(ingresso);
        return ResponseEntity.created(URI.create("/ingressos/" + saved.getId())).body(saved);
    }

    /** Admin pode deletar qualquer ingresso; usuário só pode deletar o seu. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id, Authentication auth) {
        Ingresso ingresso = repo.findById(id).orElse(null);
        if (ingresso == null) return ResponseEntity.notFound().build();

        boolean isAdmin = auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !ingresso.getUsuario().getEmail().equals(auth.getName())) {
            return ResponseEntity.status(403).build();
        }

        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
