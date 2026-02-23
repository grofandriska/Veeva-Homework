package hu.grofandriska.veeva.model.registration;

import org.springframework.http.HttpStatus;

public record RegistrationResult(HttpStatus status, String vaultId) {}
