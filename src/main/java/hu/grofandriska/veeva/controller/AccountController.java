package hu.grofandriska.veeva.controller;


import hu.grofandriska.veeva.model.account.Lead__c;
import hu.grofandriska.veeva.model.account.response.AccountResponse;
import hu.grofandriska.veeva.model.vault.VaultAuthResponse;
import hu.grofandriska.veeva.service.AccountService;
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

    public AccountController(VaultAuthService vaultAuthService, AccountService accountService) {
        this.vaultAuthService = vaultAuthService;
        this.accountService = accountService;

    }
    //(email_cda__v=james.brown1@example.com), AccountData(email_cda__v=patricia.davis2@example.com)])
    @GetMapping("/auth")
    public ResponseEntity<String> getSessionId(@RequestBody Lead__c lead__c) {
        try {
            VaultAuthResponse session = vaultAuthService.getSessionId();
            if (session.getSessionId() == null || session.getSessionId().isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve session ID");
            }
            AccountResponse s = accountService.findEmail(lead__c.getEmail__c(), session.getSessionId());

            if (s.getResponseStatus().equals("SUCCESS")) {
                if (s.getResponseDetails().getSize() == 1) {
                    return ResponseEntity.ok("VaultId: " + session.getVaultId());
                } else if (s.getResponseDetails().getSize() == 0 || s.getResponseDetails().getSize() > 1) {
                    //POST TO MOCK
                    return ResponseEntity.status(HttpStatus.CREATED).body("Created");
                }
            } else if (s.getResponseStatus().equals("WARNING")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("SQL WARNING!");
            }

        } catch (Exception e){
            System.out.println(e);
        }
        finally {

        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("UNKNOWN SERVER ERROR");
    }
}


