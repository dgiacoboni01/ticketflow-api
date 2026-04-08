package com.ingressos.api.controller;

import com.ingressos.api.model.User;
import com.ingressos.api.repository.UserRepository;
import com.ingressos.api.security.JwtService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public AuthController(UserRepository repo, PasswordEncoder encoder, JwtService jwt) {
        this.repo    = repo;
        this.encoder = encoder;
        this.jwt     = jwt;
    }

    public record LoginRequest(
        @NotBlank String email,
        @NotBlank String senha
    ) {}

    public record RegisterRequest(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres") String senha
    ) {}

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequest req) {
        return repo.findByEmail(req.email())
            .filter(u -> u.getSenha() != null && encoder.matches(req.senha(), u.getSenha()))
            .map(u -> ResponseEntity.ok(resposta(u)))
            .orElse(ResponseEntity.status(401).body(Map.of("erro", "Email ou senha inválidos")));
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody RegisterRequest req) {
        if (repo.findByEmail(req.email()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Email já cadastrado"));
        }
        User u = new User();
        u.setNome(req.nome());
        u.setEmail(req.email());
        u.setSenha(encoder.encode(req.senha()));
        u.setRole("USER");
        User saved = repo.save(u);
        return ResponseEntity.created(URI.create("/users/" + saved.getId()))
            .body(resposta(saved));
    }

    private Map<String, Object> resposta(User u) {
        return Map.of(
            "token", jwt.gerar(u.getEmail(), u.getRole(), u.getId()),
            "role",  u.getRole(),
            "nome",  u.getNome(),
            "id",    u.getId()
        );
    }
}
