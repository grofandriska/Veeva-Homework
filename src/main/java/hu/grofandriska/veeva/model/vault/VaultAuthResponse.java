package hu.grofandriska.veeva.model.vault;


import lombok.Data;

import java.util.List;

@Data
public class VaultAuthResponse {
    private Long userId;
    private String sessionId;
    private String responseStatus;
    private List <Vault> vaultIds;
}
