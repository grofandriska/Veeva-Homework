package hu.grofandriska.veeva.model.vault;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class VaultAuthResponse {
    private Long userId;
    private String sessionId;
    private String responseStatus;
    private List <Vault> vaultIds;
    private String vaultId;
}
