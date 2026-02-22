package hu.grofandriska.veeva.controller;


import hu.grofandriska.veeva.model.account.Lead;
import hu.grofandriska.veeva.model.registration.RegistrationServerResponse;
import hu.grofandriska.veeva.model.registration.responsefields.Warning;
import hu.grofandriska.veeva.model.vault.VaultAuthResponse;
import hu.grofandriska.veeva.service.registration.AccountRegistrationService;
import hu.grofandriska.veeva.service.vault.VaultAuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> registerAccount(@RequestBody Lead lead) {
        try {
            VaultAuthResponse session = vaultAuthService.authenticate();
            if (session.getSessionId() == null || session.getSessionId().isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve session ID");
            }

            RegistrationServerResponse emailResponse = accountRegistrationService.finAccountByEmail(lead.getEmailAddress(), session.getSessionId());
            if (emailResponse.getResponseStatus().equals("SUCCESS")) {
                if (emailResponse.getResponseDetails().getSize() == 1) {
                    return ResponseEntity.ok("Account already exist at VaultId: " + session.getVaultId());
                } else if (emailResponse.getResponseDetails().getSize() == 0 || emailResponse.getResponseDetails().getSize() > 1) {
                    return ResponseEntity.status(HttpStatus.CREATED).body("Registration was successful. Vault id: " + session.getSessionId());
                }
            } else if (emailResponse.getResponseStatus().equals("WARNING")) {
                StringBuilder error = new StringBuilder();
                for (Warning w : emailResponse.getWarnings()) {
                    error.append(w.getMessage());
                    error.append("\n");
                }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("SQL WARNING! \n" + error);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected Server error occurred.");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server could not handle request.");
    }
}


