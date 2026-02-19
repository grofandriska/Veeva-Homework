package hu.grofandriska.veeva.controller;


import hu.grofandriska.veeva.model.account.Lead__c;
import hu.grofandriska.veeva.model.vault.VaultAuthResponse;
import hu.grofandriska.veeva.service.AccountService;
import hu.grofandriska.veeva.service.LeadService;
import hu.grofandriska.veeva.service.VaultAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class AccountController {

    private final VaultAuthService vaultAuthService;
    private final AccountService accountService;
   /* private final LeadService leadService*/;

    public AccountController(VaultAuthService vaultAuthService, AccountService accountService) {
        this.vaultAuthService = vaultAuthService;
        this.accountService = accountService;

    }

    //(email_cda__v=james.brown1@example.com), AccountData(email_cda__v=patricia.davis2@example.com)])

    @GetMapping("/auth")
    public ResponseEntity<String> getSessionId() {
        VaultAuthResponse session = vaultAuthService.getSessionId();
        if (session.getSessionId() == null || session.getSessionId().isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve session ID");
        }
        String s = accountService.findEmail("hehehe", session.getSessionId());
        return ResponseEntity.ok(session.getSessionId() + "   VaultId: " + session.getVaultId() + " s  " + s);

    }

}
