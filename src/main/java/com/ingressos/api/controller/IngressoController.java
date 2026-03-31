package com.ingressos.api.controller;

import com.ingressos.api.model.Ingresso;
import com.ingressos.api.repository.IngressoRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/ingressos")
@CrossOrigin
public class IngressoController {

    private final IngressoRepository repo;

    public IngressoController(IngressoRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public Ingresso criar(@RequestBody Ingresso ingresso) {
        return repo.save(ingresso);
    }

    @GetMapping
    public List<Ingresso> listar() {
        return repo.findAll();
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