package hu.grofandriska.veeva.controller;


import hu.grofandriska.veeva.model.account.Lead;
import hu.grofandriska.veeva.model.registration.RegistrationResult;
import hu.grofandriska.veeva.model.vault.VaultAuthResponse;
import hu.grofandriska.veeva.model.vault.VaultIdResponse;
import hu.grofandriska.veeva.service.registration.AccountRegistrationService;
import hu.grofandriska.veeva.service.vault.VaultAuthService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final VaultAuthService vaultAuthService;
    private final AccountRegistrationService accountRegistrationService;

    public AccountController(VaultAuthService vaultAuthService,
                             AccountRegistrationService accountRegistrationService) {
        this.vaultAuthService = vaultAuthService;
        this.accountRegistrationService = accountRegistrationService;

    }

    //(email_cda__v=james.brown1@example.com), AccountData(email_cda__v=patricia.davis2@example.com)])

    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@RequestBody Lead lead) throws Exception {
        VaultAuthResponse session = vaultAuthService.authenticate();

        if (session.getSessionId() == null || session.getSessionId().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Vault session failed"
            );
        }

        RegistrationResult result = accountRegistrationService.register(
                session.getSessionId(),
                lead,
                session.getVaultId()
        );

        return ResponseEntity
                .status(result.status())
                .body(new VaultIdResponse(result.vaultId()));
    }
}


