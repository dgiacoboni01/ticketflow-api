package com.ingressos.api.controller;

import com.ingressos.api.model.User;
import com.ingressos.api.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private final UserRepository repo;

    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public ResponseEntity<User> criar(@RequestBody User user) {
        if (user.getNome() == null || user.getNome().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.save(user));
    }

    @GetMapping
    public List<User> listar() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> buscar(@PathVariable Long id) {
        return repo.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}