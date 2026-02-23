package hu.grofandriska.veeva.model.vault;

import lombok.Data;

@Data
public class VaultIdResponse {

    private String vaultId;

    public VaultIdResponse(String vaultId) {
        this.vaultId = vaultId;
    }

    public String getVaultId() {
        return vaultId;
    }
}