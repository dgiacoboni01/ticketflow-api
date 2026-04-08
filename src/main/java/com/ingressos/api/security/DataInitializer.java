package com.ingressos.api.security;

import com.ingressos.api.model.User;
import com.ingressos.api.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public DataInitializer(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        if (repo.findByEmail("admin@ticketflow.com").isEmpty()) {
            User admin = new User();
            admin.setNome("Administrador");
            admin.setEmail("admin@ticketflow.com");
            admin.setSenha(encoder.encode("admin123"));
            admin.setRole("ADMIN");
            repo.save(admin);
            System.out.println("==========================================");
            System.out.println("  Admin criado automaticamente:");
            System.out.println("  Email : admin@ticketflow.com");
            System.out.println("  Senha : admin123");
            System.out.println("==========================================");
        }
    }
}
