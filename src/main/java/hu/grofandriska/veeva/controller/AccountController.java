package hu.grofandriska.veeva.controller;


import hu.grofandriska.veeva.service.VaultAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class AccountController {

    private final VaultAuthService vaultAuthService;

    public AccountController(VaultAuthService vaultAuthService) {
        this.vaultAuthService = vaultAuthService;
    }

    @GetMapping("/auth")
    public ResponseEntity<String> getSessionId(){
        String sessionId = vaultAuthService.getSessionId();
        if (sessionId == null || sessionId.isEmpty()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) .body("Failed to retrieve session ID");
        }
        return ResponseEntity.ok(sessionId);
    }
}
